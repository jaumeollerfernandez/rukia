# Crea multimedia provisional (imágenes con su descripción y audios leídos por la voz del sistema) para todo lo que lista
# el MEDIA_PENDIENTE.md de un caso, cada archivo en su sitio. No pisa archivos que ya existan: al cambiar uno por el
# definitivo, borra el provisional y pon el tuyo con el mismo nombre.
#
# Uso (Windows PowerShell, con ffmpeg en el PATH o pasándolo con -Ffmpeg):
#   .\tools\placeholders.ps1 -Case app\src\main\assets\cases\ruk-93429049
#
# Necesita: System.Drawing y System.Speech (vienen con Windows), la voz española «Microsoft Helena Desktop» y ffmpeg.
param([Parameter(Mandatory)][string]$Case, [string]$Ffmpeg = (Get-Command ffmpeg -ErrorAction SilentlyContinue).Source)
if (-not $Ffmpeg) { throw "No encuentro ffmpeg: añádelo al PATH o pásalo con -Ffmpeg" }
$Case = (Resolve-Path $Case).Path
$ErrorActionPreference = "Stop"
Add-Type -AssemblyName System.Drawing
Add-Type -AssemblyName System.Speech

$list = Get-Content (Join-Path $Case "MEDIA_PENDIENTE.md") -Encoding UTF8
$calls = Get-Content (Join-Path $Case "story\LLAMADAS.md") -Encoding UTF8 -Raw

function Target([string]$path) {
    if ($path -like "IMG_*") { return Join-Path $Case "multimedia\$path" }
    return Join-Path $Case ("media\" + $path.Replace("/", "\"))
}

function Color([string]$seed) {
    $h = [Math]::Abs($seed.GetHashCode())
    return [System.Drawing.Color]::FromArgb(255, 40 + $h % 120, 40 + ($h -shr 8) % 120, 60 + ($h -shr 16) % 120)
}

function Image([string]$path, [string]$title, [string]$text, [int]$w, [int]$h) {
    $out = Target $path
    if (Test-Path $out) { return }
    New-Item -ItemType Directory -Force (Split-Path $out) | Out-Null
    $bmp = New-Object System.Drawing.Bitmap $w, $h
    $g = [System.Drawing.Graphics]::FromImage($bmp)
    $g.TextRenderingHint = "AntiAlias"
    $g.Clear((Color $path))
    $white = [System.Drawing.Brushes]::White
    $pad = [int]($w * 0.07)
    $small = New-Object System.Drawing.Font "Segoe UI", ([int]($w / 30)), ([System.Drawing.FontStyle]::Bold)
    $big = New-Object System.Drawing.Font "Segoe UI", ([int]($w / 14)), ([System.Drawing.FontStyle]::Bold)
    $body = New-Object System.Drawing.Font "Segoe UI", ([int]($w / 28))
    $g.DrawString("PROVISIONAL", $small, $white, $pad, $pad)
    $g.DrawString($title, $big, $white, (New-Object System.Drawing.RectangleF $pad, ($pad + $w / 18), ($w - 2 * $pad), ($h / 3)))
    if ($text) { $g.DrawString($text, $body, $white, (New-Object System.Drawing.RectangleF $pad, ($h * 0.42), ($w - 2 * $pad), ($h * 0.55))) }
    $g.DrawString($path, $small, $white, $pad, ($h - $pad - $w / 22))
    $codec = [System.Drawing.Imaging.ImageCodecInfo]::GetImageEncoders() | Where-Object { $_.MimeType -eq "image/jpeg" }
    $params = New-Object System.Drawing.Imaging.EncoderParameters 1
    $params.Param[0] = New-Object System.Drawing.Imaging.EncoderParameter ([System.Drawing.Imaging.Encoder]::Quality), 75L
    $bmp.Save($out, $codec, $params)
    $g.Dispose(); $bmp.Dispose()
    "imagen  $path"
}

function Audio([string]$path) {
    $out = Target $path
    if (Test-Path $out) { return }
    New-Item -ItemType Directory -Force (Split-Path $out) | Out-Null
    # El guion de LLAMADAS.md: las líneas citadas de su sección, sin acotaciones.
    $section = [regex]::Match($calls, "(?s)## \d+\. ``" + [regex]::Escape($path) + "``.*?(?=\n## |\z)").Value
    $lines = ($section -split "`n") | Where-Object { $_ -match "^> " } | ForEach-Object {
        ($_ -replace "^> ", "" -replace "\*\([^)]*\)\*", "" -replace "[*_]", "").Trim()
    } | Where-Object { $_ }
    $text = "Audio provisional. " + ($lines -join " ")
    $wav = [System.IO.Path]::ChangeExtension($out, ".wav")
    $tts = New-Object System.Speech.Synthesis.SpeechSynthesizer
    $tts.SelectVoice("Microsoft Helena Desktop")
    $tts.SetOutputToWaveFile($wav)
    $tts.Speak($text)
    $tts.Dispose()
    & $Ffmpeg -loglevel error -y -i $wav -ac 1 -c:a aac -b:a 64k $out
    Remove-Item $wav
    "audio   $path ($($lines.Count) frases)"
}

$section = ""
foreach ($line in $list) {
    if ($line -match "^## (\d+)\.") { $section = $Matches[1]; continue }
    if ($line -notmatch "^- \[[ x]\] ") { continue }
    $desc = ($line -replace "^- \[[ x]\] ", "" -replace "``[^``]*``", "" -replace "\*|🔎", "" -replace "^[\s,:.]+", "").Trim()
    $paths = [regex]::Matches($line, "``([^``]+)``") | ForEach-Object { $_.Groups[1].Value }
    switch ($section) {
        "1" { $paths | Where-Object { $_ -like "audio/*.m4a" } | ForEach-Object { Audio $_ } }
        "2" { $paths | Where-Object { $_ -like "IMG_*.jpg" } | ForEach-Object { Image $_ ($_ -replace "\.jpg$", "") $desc 1080 1080 } }
        "3" { $paths | Where-Object { $_ -like "gonpi/*.jpg" } | ForEach-Object { Image $_ (($_ -replace "^gonpi/", "" -replace "\.jpg$", "")) $desc 1080 1080 } }
        "4" {
            if ($line -match "Sin foto") { continue }
            $paths | ForEach-Object { Image "photos/$_.jpg" ($_.Substring(0, 1).ToUpper() + $_.Substring(1)) "" 512 512 }
        }
        "5" { $paths | Where-Object { $_ -like "places/*.jpg" } | ForEach-Object { Image $_ (($_ -replace "^places/", "" -replace "\.jpg$", "")) $desc 1080 720 } }
        "7" { $paths | Where-Object { $_ -like "chat/*.jpg" } | ForEach-Object { Image $_ (($_ -replace "^chat/", "" -replace "\.jpg$", "")) $desc 1080 810 } }
    }
}
