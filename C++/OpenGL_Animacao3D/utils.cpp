#include "utils.h"

void Vetor3f::rotacionar(float Angle, const Vetor3f& V) {
    Quaternion rotacionarQuaternion(Angle, V);

    Quaternion matrizHermit = rotacionarQuaternion.hermitializar();

    Quaternion W = rotacionarQuaternion * (*this) * matrizHermit;

    x = W.x;
    y = W.y;
    z = W.z;
}