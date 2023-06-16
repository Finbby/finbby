import os
from flask import Flask, request, jsonify
import re
from transformers import GPT2Tokenizer, TFGPT2ForSequenceClassification
import tensorflow as tf
import numpy as np

app = Flask(__name__)
num_classes = 5
tokenizer = GPT2Tokenizer.from_pretrained("gpt2")
tokenizer.add_special_tokens({"pad_token": "<pad>"})
tokenizer.padding_side = "left"

gpt2 = TFGPT2ForSequenceClassification.from_pretrained('gpt2', num_labels=num_classes)
gpt2.resize_token_embeddings(len(tokenizer))
dropout = tf.keras.layers.Dropout(0.5)

input_ids = tf.keras.Input(shape=(None,), dtype=tf.int32, name='input_ids')
attention_mask = tf.keras.Input(shape=(None,), dtype=tf.int32, name='attention_mask')

last_hidden_state = gpt2(input_ids=input_ids, attention_mask=attention_mask)[0]
pooled_output = tf.keras.layers.GlobalMaxPool1D()(last_hidden_state)
dropout_output = dropout(pooled_output)
logits = tf.keras.layers.Dense(num_classes, activation='softmax')(dropout_output)

model = tf.keras.Model(inputs=[input_ids, attention_mask], outputs=logits)
MAXLEN = 100

def post_to_sentences(posts):
    sentence_list = []
    for post in posts:
        post = str(post)
        sentences = re.split(r'(?<!\w\.\w.)(?<![A-Z][a-z]\.)(?<=\.|\?)\s', post)
        for sentence in sentences:
            sentence_list.append(sentence) 
    return sentence_list

@app.route('/predict', methods=['POST'])
def predict():
    data = request.get_json()
    input_text = data['input_text']

    # Melakukan preprocess awal
    example = post_to_sentences([input_text])
    tokenized_example = tokenizer(example, padding=True, truncation=True, max_length=MAXLEN, return_tensors="tf")
    # Melakukan inferensi
    example_ids = tokenized_example.input_ids
    example_attention_mask = tokenized_example.attention_mask

    predicted_probs = model.predict([example_ids, example_attention_mask])
    predicted_probs = tf.nn.softmax(predicted_probs).numpy()

    label_mapping = {0: 'Gaming', 1: 'Sports', 2: 'Cinema', 3: 'Books', 4: 'Cooking'}
    index = np.argmax(predicted_probs)
    predicted_label = label_mapping[index]

    # Format dan kirimkan respons sebagai JSON
    response = {
        'predicted_label': predicted_label,
        'predicted_prob': predicted_probs.tolist()
    }
    return jsonify(response)


if __name__ == '__main__':
    app.run(host="0.0.0.0", port=int(os.environ.get("PORT", 3000)))
