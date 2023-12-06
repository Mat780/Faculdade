#ifndef CAMERA_H
#define CAMERA_H

#include "utils.h"

class Camera
{
public:
    Camera(int comprimentoJanela, int alturaJanela);
    Camera(int comprimentoJanela, int alturaJanela, const Vetor3f& posicao, const Vetor3f& alvo, const Vetor3f& acima);

    void setPosicao(float x, float y, float z);

    void renderizacao();
    void ouvirTeclado(unsigned char tecla);
    void ouvirMouse(int x, int y);
    void deslocarCamera(int x, int y);

    void aumentarVelocidade(int aumento);
    void aumentarVelocidade();
    void diminiurVelocidade(int decrecimo);
    void diminiurVelocidade();

    void moverCameraParaCima();
    void moverCameraParaBaixo();
    void moverCameraParaFrente();
    void moverCameraParaAtras();
    void moverCameraParaEsquerda();
    void moverCameraParaDireita();

    void rotacionarCameraCima();
    void rotacionarCameraBaixo();
    void rotacionarCameraEsquerda();
    void rotacionarCameraDireita();

    void setAnguloHorizontal(int angulo);
    void setAnguloVertical(int angulo);
    void setVetPosicao(Vetor3f vetor);
    void setVelocidade(float novaVelocidade);

    float getAnguloHorizontal();
    float getAnguloVertical();
    float getAnguloHorizontalOriginal();
    float getAnguloVerticalOriginal();
    Vetor3f getVetPosicao();
    Vetor3f getVetAlvo();

    Matriz4f getMatriz();

private:

    void iniciar();
    void atualizar();

    Vetor3f vetPosicao;
    Vetor3f vetAlvo;
    Vetor3f vetAcima;
    float velocidade = 0.1f;

    int comprimentoDaTela;
    int alturaDaTela;

    float anguloHorizontal;
    float anguloVertical;

    float anguloHorizontalOriginal;
    float anguloVerticalOriginal;

    bool bordaSuperior;
    bool bordaInferior;
    bool bordaEsquerda;
    bool bordaDireita;

    Vetor2i posicaoMouse;
};

#endif  /* CAMERA_H */