# Prácticas-Poker-UCM-HIJA
Prácticas desarrolladas durante el curso 2014/2015 para la asignatura "Herramientas informáticas 
para los juegos de azar"

# Práctica 1:
Analizador de manos de póker, parsea los archivos "apartadoX.txt" y determina la jugada de cada mano.
Se ha implementado un evaluador de manos a modo de máquina de estados siguiendo el diagrama que aparece en la web "http://www.makinolo.com/2006/12/evaluacion-de-manos/" con algunas modificaciones para su correcto funcionamiento.

# Práctica 2:
Analizador de rangos de póker según distintas estrategias. También hay un parser que analiza si has jugado bien una mano preFlop según el ranking elegido en PokerStars.

# Práctica 3:
Versión personal de PokerStove, con resultados bastante similares a los del programa original y la posibilidad de introducir rangos polarizados como una tupla de cuatro enteros:
"0, 5, 70, 15" -> rango (0, 0+5) - (0+5+70, 0+5+70+15) -> rango (0,5)-(75,90)

# Práctica 4:
Juego de póker para probar una estrategia usando pokerstove de la práctica 3 en tiempo real.
