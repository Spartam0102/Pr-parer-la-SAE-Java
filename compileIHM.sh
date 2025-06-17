#!/bin/bash

echo "Compilation en cours..."
javac --module-path "/usr/share/openjfx/lib/" --add-modules javafx.controls,javafx.fxml -d bin src/IHM/FenetrePanier.java
if [ $? -ne 0 ]; then
    echo "❌ Erreur de compilation"
    exit 1
fi
echo "✓ Compilation réussie"

echo "Lancement de l'application..."
java --module-path "/usr/share/openjfx/lib/" --add-modules javafx.controls,javafx.fxml -cp bin IHM.FenetrePanier
