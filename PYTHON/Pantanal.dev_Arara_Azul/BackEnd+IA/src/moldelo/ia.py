from transformers import AutoTokenizer, TFAutoModelForSequenceClassification
from tensorflow import keras
import tensorflow as tf

modelo = None


def carregar_modelo():
    global modelo
    if modelo is None:
        modelo = TFAutoModelForSequenceClassification.from_pretrained("bert-base-cased", num_labels=3)
        loss = tf.keras.losses.SparseCategoricalCrossentropy(from_logits=True)
        modelo.compile(optimizer='adam', loss=loss, metrics=['accuracy'])
        modelo.load_weights('./src/moldelo/model/')


def preparar(reviews):
    tokenizer = AutoTokenizer.from_pretrained("bert-base-cased")
    tokenized_data = tokenizer(reviews, return_tensors="np", truncation=True, padding=True, max_length=512)
    return dict(tokenized_data)


def predecit(reviews):
    pred = modelo.predict(preparar(reviews))
    probas = pred.logits
    predicted_class = tf.argmax(probas, axis=-1)
    return predicted_class.numpy().tolist()
