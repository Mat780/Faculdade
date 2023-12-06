#version 330 core

layout(location = 0) in vec3 vertexPosition;
layout(location = 1) in vec3 cor;
layout(location = 2) in vec3 normal;

uniform mat4 mundo;
uniform mat4 mundoSemRotacao;
uniform float wAlternado;
uniform vec3 cameraPos;
uniform mat4 projecaoEVisao;

out vec3 posicaoCamera;
out vec3 color;
out vec3 normalF;
out vec3 FragPos;
out vec3 posicaoLuz;

void main() {
    gl_Position = mundo * vec4(vertexPosition, wAlternado);

    posicaoLuz = vec3( (mundoSemRotacao * vec4(-0.3, 0, 0, 1.0)).xyz );
    posicaoCamera = vec3( (vec4(cameraPos, 1) * projecaoEVisao).xyz );

    FragPos = vec3(gl_Position.xyz);
    color = cor;
    normalF = vec3( (vec4(normal, 1) * mundoSemRotacao).xyz );
}