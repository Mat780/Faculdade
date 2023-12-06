#include "camera.h"
#include <GL/freeglut.h>
#include <iostream>

static int MARGEM = 8;
static float PASSO_BORDA = 1.0f;

Camera::Camera(int comprimentoJanela, int alturaJanela) {

    this->comprimentoDaTela = comprimentoJanela;
    this->alturaDaTela = alturaJanela;

    vetPosicao = Vetor3f(0.0f, 0.0f, 0.0f);
    vetAlvo    = Vetor3f(0.0f, 0.0f, 1.0f);
    vetAcima   = Vetor3f(0.0f, 1.0f, 0.0f);
}

Camera::Camera(int comprimentoJanela, int alturaJanela, const Vetor3f& posicao, const Vetor3f& alvo, const Vetor3f& acima) {

    this->comprimentoDaTela = comprimentoJanela;
    this->alturaDaTela = alturaJanela;
    this->vetPosicao = posicao;

    this->vetAlvo = alvo;
    vetAlvo.normalizar();

    this->vetAcima = acima;
    vetAcima.normalizar();

    iniciar();

}

void Camera::iniciar() {

    Vetor3f alvoHorizontal(vetAlvo.x, 0.0, vetAlvo.z);
    alvoHorizontal.normalizar();

    float angulo = paraAngulo(asin(abs(alvoHorizontal.z)));

    if (alvoHorizontal.z >= 0.0f) {
        if (alvoHorizontal.x >= 0.0f) {
            anguloHorizontal = 360.0f - angulo;
            anguloHorizontalOriginal = 360.0f - angulo;
        
        } else {
            anguloHorizontal = 180.0f + angulo;
            anguloHorizontalOriginal = 180.0f + angulo;
        }
    }
    else {
        if (alvoHorizontal.x >= 0.0f) {
            anguloHorizontal = angulo;
            anguloHorizontalOriginal = angulo;
        
        } else {
            anguloHorizontal = 180.0f - angulo;
            anguloHorizontalOriginal = 180.0f - angulo;
        }
    }

    anguloVertical = -paraAngulo(asin(vetAlvo.y));
    anguloVerticalOriginal = -paraAngulo(asin(vetAlvo.y));

    bordaSuperior = false;
    bordaInferior = false;
    bordaEsquerda  = false;
    bordaDireita = false;
    posicaoMouse.x  = comprimentoDaTela / 2;
    posicaoMouse.y  = alturaDaTela / 2;

}

void Camera::setPosicao(float x, float y, float z) {
    vetPosicao.x = x;
    vetPosicao.y = y;
    vetPosicao.z = z;
}

void Camera::ouvirTeclado(unsigned char tecla) {
    switch (tecla) {

    case GLUT_KEY_UP:
        moverCameraParaFrente();
        break;

    case GLUT_KEY_DOWN:
        moverCameraParaAtras();
        break;

    case GLUT_KEY_LEFT:
        moverCameraParaEsquerda();
        break;

    case GLUT_KEY_RIGHT:
        moverCameraParaDireita();
        break;

    case GLUT_KEY_PAGE_UP:
        moverCameraParaCima();
        break;

    case GLUT_KEY_PAGE_DOWN:
        moverCameraParaBaixo();
        break;

    case '+':
        aumentarVelocidade();
        break;

    case '-':
        diminiurVelocidade();
        break;
    }
}

void Camera::aumentarVelocidade(int aumento) {
    velocidade += aumento;
    printf("Velocidade mudou para:  %f\n", velocidade);
}

void Camera::aumentarVelocidade() {
    velocidade += 0.1f;
    printf("Velocidade mudou para:  %f\n", velocidade);
}

void Camera::diminiurVelocidade(int decrecimo) {
    velocidade -= decrecimo;
    if (velocidade < 0.1f) {
        velocidade = 0.1f;
    }
    printf("Velocidade mudou para:  %f\n", velocidade);
}

void Camera::diminiurVelocidade() {
    velocidade -= 0.1f;
    if (velocidade < 0.1f) {
        velocidade = 0.1f;
    }
    printf("Velocidade mudou para:  %f\n", velocidade);
}

void Camera::moverCameraParaCima() {
    vetPosicao.y += velocidade;
}
void Camera::moverCameraParaBaixo() {
    vetPosicao.y -= velocidade;
}

void Camera::moverCameraParaFrente() {
    vetPosicao += (vetAlvo * velocidade);
}
void Camera::moverCameraParaAtras() {
    vetPosicao -= (vetAlvo * velocidade);
}
void Camera::moverCameraParaEsquerda() {
    Vetor3f esquerda = vetAlvo.produtoVetorial(vetAcima);
    esquerda.normalizar();
    esquerda *= velocidade;
    vetPosicao += esquerda;
}
void Camera::moverCameraParaDireita() {
    Vetor3f direita = vetAcima.produtoVetorial(vetAlvo);
    direita.normalizar();
    direita *= velocidade;
    vetPosicao += direita;
}

void Camera::ouvirMouse(int x, int y) {
    deslocarCamera(x, y);
}

void Camera::deslocarCamera(int x, int y) {
    int deltaX = x - posicaoMouse.x;
    int deltaY = y - posicaoMouse.y;

    posicaoMouse.x = x;
    posicaoMouse.y = y;

    anguloHorizontal += (float)deltaX / 20.0f;
    anguloVertical += (float)deltaY / 50.0f;

    if (deltaX == 0) {
        if (x <= MARGEM) {
            bordaEsquerda = true;
        }
        else if (x >= (comprimentoDaTela - MARGEM)) {
            bordaDireita = true;
        }
    }
    else {
        bordaEsquerda = false;
        bordaDireita = false;
    }

    if (deltaY == 0) {
        if (y <= MARGEM) {
            bordaSuperior = true;
        }
        else if (y >= (alturaDaTela - MARGEM)) {
            bordaInferior = true;
        }
    }
    else {
        bordaSuperior = false;
        bordaInferior = false;
    }

    atualizar();
}

void Camera::rotacionarCameraCima() {
    anguloVertical -= PASSO_BORDA;
    if (anguloVertical < 0.0) anguloVertical += 360;
    atualizar();
}

void Camera::rotacionarCameraBaixo() {
    anguloVertical += PASSO_BORDA;
    if (anguloVertical >= 360.0) anguloVertical -= 360;
    atualizar();
}

void Camera::rotacionarCameraEsquerda() {
    anguloHorizontal -= PASSO_BORDA;
    if (anguloHorizontal < 0.0) anguloHorizontal += 360;
    atualizar();
}

void Camera::rotacionarCameraDireita() {
    anguloHorizontal += PASSO_BORDA;
    if (anguloHorizontal >= 360.0) anguloHorizontal -= 360;
    atualizar();
}

void Camera::renderizacao() {
    bool precisaAtualizar = false;

    if (bordaEsquerda) {
        anguloHorizontal -= PASSO_BORDA;
        precisaAtualizar = true;
    }
    else if (bordaDireita) {
        anguloHorizontal += PASSO_BORDA;
        precisaAtualizar = true;
    }

    if (bordaSuperior) {
        if (anguloVertical > -90.0f) {
            anguloVertical -= PASSO_BORDA;
            precisaAtualizar = true;
        }
    }
    else if (bordaInferior) {
        if (anguloVertical < 90.0f) {
           anguloVertical += PASSO_BORDA;
           precisaAtualizar = true;
        }
    }

    if (precisaAtualizar) {
        atualizar();
    }
}

void Camera::atualizar() {
    Vetor3f Yaxis(0.0f, 1.0f, 0.0f);

    // Rotate the visao vector by the horizontal angulo around the vertical axis
    Vetor3f visao(1.0f, 0.0f, 0.0f);
    visao.rotacionar(anguloHorizontal, Yaxis);
    visao.normalizar();

    // Rotate the visao vector by the vertical angulo around the horizontal axis
    Vetor3f U = Yaxis.produtoVetorial(visao);
    U.normalizar();
    visao.rotacionar(anguloVertical, U);

    vetAlvo = visao;
    vetAlvo.normalizar();

    vetAcima = vetAlvo.produtoVetorial(U);
    vetAcima.normalizar();
}

void Camera::setAnguloHorizontal(int angulo) {
    anguloHorizontal = angulo;
}

void Camera::setAnguloVertical(int angulo) {
    anguloVertical = angulo;
}

void Camera::setVetPosicao(Vetor3f vetor) {
    vetPosicao = vetor;
}

void Camera::setVelocidade(float novaVelocidade) {
    velocidade = novaVelocidade;
}

float Camera::getAnguloHorizontal() {
    return anguloHorizontal;
}
float Camera::getAnguloVertical() {
    return anguloVertical;
}
float Camera::getAnguloHorizontalOriginal(){
    return anguloHorizontalOriginal;
}
float Camera::getAnguloVerticalOriginal() {
    return anguloVerticalOriginal;
}

Vetor3f Camera::getVetPosicao() {
    return vetPosicao;
}

Vetor3f Camera::getVetAlvo() {
    return vetAlvo;
}

Matriz4f Camera::getMatriz() {
    Matriz4f transformacaoCamera;
    transformacaoCamera.iniciaTransformacaoCamera(vetPosicao, vetAlvo, vetAcima);

    return transformacaoCamera;
}

