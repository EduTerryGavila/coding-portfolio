import os
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '3'  # Solo errores fatales (quita warnings e info)
import tensorflow as tf

from tensorflow import keras

# Codificación one-hot: R = [1,0,0], P = [0,1,0], S = [0,0,1]
def codificar_jugada(movimiento):
    if movimiento == "R":
        return tf.constant([1, 0, 0], dtype=tf.float32)
    elif movimiento == "P":
        return tf.constant([0, 1, 0], dtype=tf.float32)
    elif movimiento == "S":
        return tf.constant([0, 0, 1], dtype=tf.float32)
    else:
        return tf.constant([0, 0, 0], dtype=tf.float32)

# Crear modelo una sola vez (fuera de la función)
model = keras.Sequential([
    keras.layers.Dense(32, activation='relu', input_shape=(9,)),
    keras.layers.Dense(3, activation='softmax')
])
model.compile(optimizer='adam', loss='categorical_crossentropy', metrics=['accuracy'])

# Historial para guardar entradas y salidas
entrenamiento_X = []
entrenamiento_Y = []

def player(prev_play, opponent_history=[]):
    global entrenamiento_X, entrenamiento_Y

    # Guardar jugada previa del oponente
    if prev_play != "":
        opponent_history.append(prev_play)

    # Tomar las últimas 3 jugadas
    ult_jugadas = opponent_history[-3:]

    # Codificar a vectores
    vectores = [codificar_jugada(m) for m in ult_jugadas]

    # Rellenar si hay menos de 3
    while len(vectores) < 3:
        vectores.insert(0, tf.constant([0, 0, 0], dtype=tf.float32))

    # Unir en un solo vector largo (input de 9 valores)
    input_vector = tf.concat(vectores, axis=0)

    # Si hay suficientes jugadas, guardar (X, Y) para entrenamiento
    if len(opponent_history) > 3:
        X = input_vector
        Y = codificar_jugada(opponent_history[-1])
        entrenamiento_X.append(X)
        entrenamiento_Y.append(Y)

        # Convertir a tensor
        X_tensor = tf.stack(entrenamiento_X)
        Y_tensor = tf.stack(entrenamiento_Y)

        # Entrenar 1 epoch cada vez (online learning)
        model.fit(X_tensor, Y_tensor, epochs=1, verbose=0)

    # Predecir próxima jugada del oponente
    pred = model.predict(tf.expand_dims(input_vector, axis=0), verbose=0)
    pred_index = tf.argmax(pred[0]).numpy()
    index_to_move = {0: "R", 1: "P", 2: "S"}
    prediccion_oponente = index_to_move[pred_index]

    # Elegir la jugada que le gana
    counter = {"R": "P", "P": "S", "S": "R"}
    return counter[prediccion_oponente]
