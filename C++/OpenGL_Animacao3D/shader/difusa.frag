#version 330 core

in vec3 color;
in vec3 FragPos;
in vec3 normalF;
in vec3 posicaoLuz;

out vec4 FragColor;

uniform float kA;
uniform float kD;

void main() {
    vec3 corLuz = vec3(1.0,1.0,1.0); //luz branca
    vec3 ambiente = kA * corLuz; 

    vec3 direcaoLuz = normalize(posicaoLuz - FragPos);

    float fatorDifuso = dot(direcaoLuz, normalF);
    
    float cosTeta = clamp(fatorDifuso, 0.0, 1.0);
    vec3 difusa = kD * corLuz * cosTeta;
    
    float dist = length(posicaoLuz - FragPos);
    float atenuacao = 1.0/(1+0.09*dist+0.032*dist*dist);

    vec3 result = difusa * color * atenuacao;
    FragColor = vec4(result, 1.0f);
}

