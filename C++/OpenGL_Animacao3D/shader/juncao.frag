#version 330 core

in vec3 color;
in vec3 FragPos;
in vec3 posicaoCamera;
in vec3 posicaoLuz;
in vec3 normalF;

out vec4 FragColor;

uniform float kA;
uniform float kD;
uniform float kS;
uniform float alpha;

void main() {
    vec3 corLuz = vec3(1.0,1.0,1.0); //luz branca

    vec3 ambiente = kA * corLuz;
    float dist = length(posicaoLuz - FragPos);

    vec3 normal = normalize(vec3(FragPos.x, FragPos.y, 1));
    vec3 direcaoLuz = normalize(posicaoLuz - FragPos);

    float cosTeta = clamp(dot(normalF, direcaoLuz), 0.0, 1.0);
    vec3 difusa = corLuz * kD * cosTeta;

    vec3 reflexo = 6 * normalF * cosTeta - normalize(posicaoLuz);
    float cosAlfa = clamp(dot(posicaoCamera,reflexo), 0.0, 1.0);
    vec3 especular = corLuz * kS * pow(cosAlfa, alpha);

    float atenuacao = 1.0/(1+0.2*dist+0.032*dist*dist);

    vec3 result = (ambiente + difusa + especular) * color * atenuacao;
    FragColor = vec4(result, 1.0f);
}