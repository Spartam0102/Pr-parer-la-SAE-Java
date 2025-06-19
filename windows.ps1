$sourcesFile = "sources.txt"
if (Test-Path $sourcesFile) {
    Remove-Item $sourcesFile
}

Get-ChildItem -Recurse -Filter *.java -Path src | Where-Object {
    $_.FullName -notmatch 'src\\App\\'
} | ForEach-Object {
    $_.FullName | Out-File -FilePath $sourcesFile -Append -Encoding ascii
}

$javacArgs = @(
    "--module-path", "C:\java\drivers\openjfx-24.0.1_windows-x64_bin-sdk\javafx-sdk-24.0.1\lib",
    "--add-modules", "javafx.controls,javafx.fxml",
    "-cp", "lib\\mariadb-java-client-2.5.3.jar",
    "-d", "bin",
    "@sources.txt"
)

& javac @javacArgs

if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Erreur de compilation"
    Remove-Item $sourcesFile
    exit 1
}

Remove-Item $sourcesFile

$javaArgs = @(
    "--module-path", "C:\java\drivers\openjfx-24.0.1_windows-x64_bin-sdk\javafx-sdk-24.0.1\lib",
    "--add-modules", "javafx.controls,javafx.fxml",
    "-cp", "bin;lib\\mariadb-java-client-2.5.3.jar",
    "IHM.FenetreConnexion"
)

& java @javaArgs
