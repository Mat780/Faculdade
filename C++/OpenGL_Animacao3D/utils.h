#include <stdio.h>
#include <cmath>
#include <assert.h>

#define PI 3.14159265358979323846

#define paraRadianos(angulo) ((float) angulo * PI / 180)
#define paraAngulo(radianos) ((float) radianos * 180.0f / PI)

struct Vetor2i {
    int x;
    int y;
};

struct Vetor3f {
    union {
        float x;
        float r;
    };

    union {
        float y;
        float g;
    };

    union {
        float z;
        float b;
    };

    Vetor3f() {}

    Vetor3f(float _x, float _y, float _z) {
        x = _x;
        y = _y;
        z = _z;
    }

    Vetor3f& operator+=(const Vetor3f& r) {
        x += r.x;
        y += r.y;
        z += r.z;

        return *this;
    }

    Vetor3f& operator-=(const Vetor3f& r) {
        x -= r.x;
        y -= r.y;
        z -= r.z;

        return *this;
    }

    Vetor3f& operator*=(float f) {
        x *= f;
        y *= f;
        z *= f;

        return *this;
    }

    operator const float*() const {
        return &(x);
    }

    float calcularArea() const {
        float area = sqrtf(x * x + y * y + z * z);
        return area;
    }

    Vetor3f produtoVetorial(const Vetor3f& v) const {
        const float _x = y * v.z - z * v.y; 
        const float _y = z * v.x - x * v.z;
        const float _z = x * v.y - y * v.x;

        return Vetor3f(_x, _y, _z);
    }

    Vetor3f& normalizar() {
        float area = calcularArea();

        assert(area != 0);

        x /= area;
        y /= area;
        z /= area;

        return *this;
    }

    void rotacionar(float Angle, const Vetor3f& V);

};

struct Vetor4f {
    float x;
    float y;
    float z;
    float w;

    Vetor4f() {}

    Vetor4f(float _x, float _y, float _z, float _w) {
        x = _x;
        y = _y;
        z = _z;
        w = _w;
    }

};

class Matriz4f {
    public:
        float m[4][4];

        Matriz4f() {}

        Matriz4f(float a00, float a01, float a02, float a03,
                float a10, float a11, float a12, float a13,
                float a20, float a21, float a22, float a23,
                float a30, float a31, float a32, float a33) {

            m[0][0] = a00; m[0][1] = a01; m[0][2] = a02; m[0][3] = a03;
            m[1][0] = a10; m[1][1] = a11; m[1][2] = a12; m[1][3] = a13;
            m[2][0] = a20; m[2][1] = a21; m[2][2] = a22; m[2][3] = a23;
            m[3][0] = a30; m[3][1] = a31; m[3][2] = a32; m[3][3] = a33;
        }

        inline Matriz4f operator*(const Matriz4f& direita) const {
            Matriz4f resultado;

            for (unsigned int i = 0 ; i < 4 ; i++) {
                for (unsigned int j = 0 ; j < 4 ; j++) {
                    resultado.m[i][j] = m[i][0] * direita.m[0][j] +
                                        m[i][1] * direita.m[1][j] +
                                        m[i][2] * direita.m[2][j] +
                                        m[i][3] * direita.m[3][j];
                }
            }

            return resultado;
        }

        Vetor4f operator*(const Vetor4f& v) const {
            Vetor4f r;

            r.x = m[0][0]* v.x + m[0][1]* v.y + m[0][2]* v.z + m[0][3]* v.w;
            r.y = m[1][0]* v.x + m[1][1]* v.y + m[1][2]* v.z + m[1][3]* v.w;
            r.z = m[2][0]* v.x + m[2][1]* v.y + m[2][2]* v.z + m[2][3]* v.w;
            r.w = m[3][0]* v.x + m[3][1]* v.y + m[3][2]* v.z + m[3][3]* v.w;

            return r;
        }

        operator const float*() const {
            return &(m[0][0]);
        }

        void iniciaTransformacaoTranslacao(float x, float y, float z) {
            m[0][0] = 1.0f; m[0][1] = 0.0f; m[0][2] = 0.0f; m[0][3] = x;
            m[1][0] = 0.0f; m[1][1] = 1.0f; m[1][2] = 0.0f; m[1][3] = y;
            m[2][0] = 0.0f; m[2][1] = 0.0f; m[2][2] = 1.0f; m[2][3] = z;
            m[3][0] = 0.0f; m[3][1] = 0.0f; m[3][2] = 0.0f; m[3][3] = 1.0f;
        }

        void iniciaTransformacaoTranslacao(const Vetor3f& pos) {
            iniciaTransformacaoTranslacao(pos.x, pos.y, pos.z);
        }

        void iniciaTransformacaoCamera(const Vetor3f& alvo, const Vetor3f& cima) {
            Vetor3f N = alvo;
            N.normalizar();

            Vetor3f cimaNormalizado = cima;
            cimaNormalizado.normalizar();

            Vetor3f U;
            U = cimaNormalizado.produtoVetorial(N);
            U.normalizar();

            Vetor3f V = N.produtoVetorial(U);

            m[0][0] = U.x;   m[0][1] = U.y;   m[0][2] = U.z;   m[0][3] = 0.0f;
            m[1][0] = V.x;   m[1][1] = V.y;   m[1][2] = V.z;   m[1][3] = 0.0f;
            m[2][0] = N.x;   m[2][1] = N.y;   m[2][2] = N.z;   m[2][3] = 0.0f;
            m[3][0] = 0.0f;  m[3][1] = 0.0f;  m[3][2] = 0.0f;  m[3][3] = 1.0f;
        }

        void iniciaTransformacaoCamera(const Vetor3f& pos, const Vetor3f& alvo, const Vetor3f& cima) {
            Matriz4f translacaoDaCamera;
            translacaoDaCamera.iniciaTransformacaoTranslacao(-pos.x, -pos.y, -pos.z);

            Matriz4f rotacaoDaCamera;
            rotacaoDaCamera.iniciaTransformacaoCamera(alvo, cima);

            *this = rotacaoDaCamera * translacaoDaCamera;
        }
};

struct Quaternion {
    float x, y, z, w;

    Quaternion(float Angle, const Vetor3f& V) {
        float HalfAngleInRadians = paraRadianos(Angle/2);

        float SineHalfAngle = sinf(HalfAngleInRadians);
        float CosHalfAngle = cosf(HalfAngleInRadians);

        x = V.x * SineHalfAngle;
        y = V.y * SineHalfAngle;
        z = V.z * SineHalfAngle;
        w = CosHalfAngle;
    }

    Quaternion(float _x, float _y, float _z, float _w) {
        x = _x;
        y = _y;
        z = _z;
        w = _w;
    }

    void normalizar() {
        float Length = sqrtf(x * x + y * y + z * z + w * w);

        x /= Length;
        y /= Length;
        z /= Length;
        w /= Length;
    }

    Quaternion hermitializar() const {
        Quaternion resultado(-x, -y, -z, w);
        return resultado;
    }

    Vetor3f paraAnguloQuaternion() {
        float f[3];

        f[0] = atan2(x * z + y * w, x * w - y * z);
        f[1] = acos(-x * x - y * y - z * z - w * w);
        f[2] = atan2(x * z - y * w, x * w + y * z);

        f[0] = paraAngulo(f[0]);
        f[1] = paraAngulo(f[1]);
        f[2] = paraAngulo(f[2]);

        return Vetor3f(f[0], f[1], f[2]);
    }
};

inline Quaternion operator*(const Quaternion& q, const Vetor3f& v) {
    float w = - (q.x * v.x) - (q.y * v.y) - (q.z * v.z);
    float x =   (q.w * v.x) + (q.y * v.z) - (q.z * v.y);
    float y =   (q.w * v.y) + (q.z * v.x) - (q.x * v.z);
    float z =   (q.w * v.z) + (q.x * v.y) - (q.y * v.x);

    Quaternion resultado(x, y, z, w);

    return resultado;
}

inline Quaternion operator*(const Quaternion& l, const Quaternion& r) {
    float w = (l.w * r.w) - (l.x * r.x) - (l.y * r.y) - (l.z * r.z);
    float x = (l.x * r.w) + (l.w * r.x) + (l.y * r.z) - (l.z * r.y);
    float y = (l.y * r.w) + (l.w * r.y) + (l.z * r.x) - (l.x * r.z);
    float z = (l.z * r.w) + (l.w * r.z) + (l.x * r.y) - (l.y * r.x);

    Quaternion resultado(x, y, z, w);

    return resultado;
}

inline Vetor3f operator+(const Vetor3f& e, const Vetor3f& d) {
    Vetor3f resultado(e.x + d.x,
                e.y + d.y,
                e.z + d.z);

    return resultado;
}

inline Vetor3f operator-(const Vetor3f& e, const Vetor3f& d) {
    Vetor3f resultado(e.x - d.x,
                e.y - d.y,
                e.z - d.z);

    return resultado;
}

inline Vetor3f operator*(const Vetor3f& e, float numero) {
    Vetor3f resultado(e.x * numero,
                e.y * numero,
                e.z * numero);

    return resultado;
}

inline Vetor3f operator/(const Vetor3f& e, float numero) {
    Vetor3f resultado(e.x / numero,
                e.y / numero,
                e.z / numero);

    return resultado;
}
