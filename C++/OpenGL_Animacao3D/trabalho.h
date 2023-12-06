#ifndef OPENGLCONTEXT_H
#define OPENGLCONTEXT_H
#include <string>
#include "camera.h"

using namespace std;

struct Vertex {
	float pontos[3];
	float cores[3];
    float normal[3];

	Vertex() {}

	Vertex(float x, float y, float z, float rgb[]) {
		pontos[0] = x;
		pontos[1] = y;
		pontos[2] = z;

		cores[0] = rgb[0];
		cores[1] = rgb[1];
		cores[2] = rgb[2];
	}

    Vetor3f getVertices() {
        return Vetor3f(pontos[0], pontos[1], pontos[2]);
    }

    void adicionarNormal(float x, float y, float z) {
        normal[0] = x;
        normal[1] = y;
        normal[2] = z;
    }

};

class OpenGLContext {
public:
    OpenGLContext(int argc, char *argv[], int opcao); // Constructor
    ~OpenGLContext();    // Destructor

    void printVersion() const; // Show OpenGL Version
    void rodarLoop(); // Loop enquanto o programa rodar

private:
    unsigned int VAOId;
    unsigned int VBOId;
    unsigned int IBOId;
    unsigned int programId;
    int opcaoShader;

    int anguloRotacao;
    float wAlternado;
    int estadoCamera;
    int segundosEsperaAnimacao;

    Vetor3f cameraPosOriginal;
    Vertex vertices[55];
    unsigned int indices[78];
    Vetor3f vetorNormal[55];

    static void glutRenderCallback();                       // Render window
    static void glutReshapeCallback(int width, int height); // Reshape window

    void inicializar();  // Inicia a renderizacao
    void renderizar();  // Renderiza na tela
    void rotacionar();
    void efeitos();
    void moverCamera();
    void iluminacao();
    void criarBufferDosVertex();
    void criarBufferDosIndices();
    void criarBufferDasNormais();
    void finalize() const; // Finaliza o programa

    unsigned int loadAndCompileShader(const std::string& filename, const int glType) const;
    unsigned int linkShaderProgram(unsigned int vertexShaderId, unsigned int fragmentShaderId) const;
};

#endif // OPENGLCONTEXT_H
