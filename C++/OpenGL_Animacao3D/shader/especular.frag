#version 330 core

in vec3 color;
in vec3 FragPos;
in vec3 posicaoCamera;
in vec3 posicaoLuz;
in vec3 normalF;

out vec4 FragColor;

uniform float kS;
uniform float alpha;

void main() {
    vec3 corLuz = vec3(1.0,1.0,1.0); //luz branca

    vec3 direcaoLuz = normalize(posicaoLuz - FragPos);

    float cosTheta = clamp(dot(normalF, direcaoLuz), 0.0, 1.0);

    vec3 reflexo = 7 * normalF * cosTheta - normalize(posicaoLuz);
    float cosAlfa = clamp(dot(posicaoCamera, reflexo), 0.0, 1.0); // Muda a intensidade
    
    vec3 especular = corLuz * kS * pow(cosAlfa, alpha);

    float dist = length(posicaoLuz - FragPos);
    float atenuacao = 1.0/(1 + 0.09*dist + 0.032*dist*dist);

    vec3 result = especular * color * atenuacao;
    FragColor = vec4(result, 1.0f);
}

