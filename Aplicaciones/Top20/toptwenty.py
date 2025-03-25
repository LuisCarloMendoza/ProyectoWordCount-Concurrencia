def extraer_top20(archivo_entrada, archivo_salida, n=20):
    with open(archivo_entrada, 'r', encoding='utf-8') as entrada, open(archivo_salida, 'w', encoding='utf-8') as salida:
        for i, linea in enumerate(entrada):
            if i >= n:
                break
            salida.write(linea)


extraer_top20("freq-results-sorted.txt", "top20-1.txt")