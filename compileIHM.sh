echo "📦 Compilation en cours..."

# Trouver tous les .java sauf ceux dans src/App/
find src -name "*.java" ! -path "src/App/*" > sources.txt

# Compilation avec JavaFX
javac --module-path "/usr/share/openjfx/lib/" \
      --add-modules javafx.controls,javafx.fxml \
      -d bin @sources.txt

# Vérification du succès de la compilation
if [ $? -ne 0 ]; then
    echo "❌ Erreur de compilation"
    rm sources.txt
    exit 1
fi

rm sources.txt
echo "✅ Compilation réussie"

echo "🚀 Lancement de l'application..."

# Exécution de la classe principale
java --module-path "/usr/share/openjfx/lib/" \
     --add-modules javafx.controls,javafx.fxml \
     -cp bin IHM.FenetreConnexion
