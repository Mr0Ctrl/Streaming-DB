#!/bin/bash
# filepath: run.sh

# 1. JDBC sürücüsünün yolu
JDBC_JAR="lib/mysql-connector-j-9.3.0.jar"

# 2. Derleme dizini
OUT_DIR="out"

# 3. Kaynak kod dizini
SRC_DIR="src"

# 4. Derleme: out klasörünü oluştur ve tüm .java dosyalarını derle
mkdir -p $OUT_DIR
find $SRC_DIR -name "*.java" > sources.txt
javac -cp ".:$JDBC_JAR" -d $OUT_DIR @sources.txt

# 5. Çalıştırma: Main sınıfını başlat (paketli ise tam adını yazmalısın)
java -cp ".:$OUT_DIR:$JDBC_JAR" Main

# 6. Temizlik
rm sources.txt