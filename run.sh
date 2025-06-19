!/bin/bash

echo "Compilation en cours..."
javac -cp lib/mariadb-java-client-2.5.3.jar -d bin $(find src -name "*.java")
if [ $? -ne 0 ]; then
    echo "❌ Erreur de compilation"
    exit 1
fi
echo "✓ Compilation réussie"

echo "Lancement de l'application..."
java -cp "bin:lib/mariadb-java-client-2.5.3.jar" App.Executable