#version 330 core

in vec3 color;
in vec3 FragPos;
in vec3 posicaoLuz;

out vec4 FragColor;

uniform float kA;

void main() {
    vec3 corLuz = vec3(1.0,1.0,1.0); //luz branca

    vec3 ambiente = kA * corLuz;
    float dist = length(posicaoLuz - FragPos);

    float atenuacao = 1.0/(1 + 0.2*dist + 0.5*dist*dist);

    vec3 result = ambiente * color;
    FragColor = vec4(result, 1.0f);
}