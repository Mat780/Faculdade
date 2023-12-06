// Compile command:
// Linux
// g++ --std=c++11 hello.cpp -o main -lGLEW -lGL -lGLU -lglut
// Windows
// g++ --std=c++11 hello.cpp -o main -lglew32 -lfreeglut -lglu32 -lopengl32

/*
Discente: Giovanna Rodrigues Mendes 		- 2021.1904.032-7
		  Guilherme Bachenheimer de Almeida - 2021.1904.007-6
		  Matheus Felipe Alves Duraes 		- 2021.1904.008-4

Docente: Jonathan de Andrade Silva
Disciplina: Computacao Grafica - T01-2023-2
Curso: Ciencia da Computacao
*/

#include "trabalho.h"
#include <string>
#include <vector>
#include <iostream>
#include <fstream>
#include <stdexcept>
#include <chrono>

#define GL_GLEXT_PROTOTYPES 1
#define GL3_PROTOTYPES 1
#include <GL/glew.h>
#include <GL/freeglut.h>  // ou glut.h - GLUT, include glu.h and gl.h
#include <GL/gl.h>

#define TELA_COMPRIMENTO 1366
#define TELA_ALTURA 768

using namespace std;
using namespace chrono;

namespace {
	OpenGLContext* currentInstance = nullptr;
}

Vetor3f posicaoCamera(0.0f, 0.0f, -1.0f);
Vetor3f alvoCamera(0.0f, 0.0f, 1.0f);
Vetor3f cimaCamera(0.0f, 1.0f, 0.0f);
Camera camera = Camera(TELA_COMPRIMENTO, TELA_ALTURA, posicaoCamera, alvoCamera, cimaCamera);

auto temporizador = high_resolution_clock::now();

static void ouvirTecladoCallback(unsigned char tecla, int mousePosX, int mousePosY) {
	
	if (tecla == 'q' || tecla == 27) {
        exit(0);
    }

	camera.ouvirTeclado(tecla);
}

static void ouvirTecladoEspecialCallback(int tecla, int mousePosX, int mousePosY) {
	camera.ouvirTeclado(tecla);
}

static void mousePassivoCallback(int x, int y) {
	//camera.ouvirMouse(x, y);
}

OpenGLContext::OpenGLContext(int argc, char *argv[], int opcao) {
	glutInit(&argc, argv);     // Inicializa o glut

	glutInitContextVersion(3, 0);               // IMPORTANT!
	glutInitContextProfile(GLUT_CORE_PROFILE); // IMPORTANT! or later versions, core was introduced only with 3.2
	glutInitDisplayMode(GLUT_DOUBLE | GLUT_RGB | GLUT_DEPTH); // IMPORTANT! Double buffering!

	// glutInitWindowSize(640, 480); 	// Tamanho original
	glutInitWindowSize(1366, 768); 	 	// Tamanho da tela 
	glutCreateWindow("Trabalho: Parte 2"); 	// Da um titulo a tela
	
	// Registra uma funcao de callback para o evento de repintar a tela
	glutDisplayFunc(OpenGLContext::glutRenderCallback); 
	
	// Registra uma funcao de callback para quando a janela for redimensionada
	glutReshapeFunc(OpenGLContext::glutReshapeCallback); 
	
	GLenum error = glewInit(); // Inicializa o Glew, verificando se ocorreu um erro

	if (error != GLEW_OK) {
		throw runtime_error("Erro ao inicializar o GLEW.");
	}

	currentInstance = this;

	this->opcaoShader = opcao;
	this->anguloRotacao = 45;
	this->wAlternado = 1.0f;
	this->estadoCamera = 0;
	this->cameraPosOriginal = camera.getVetPosicao();
	this->segundosEsperaAnimacao = 5;

	glEnable(GL_CULL_FACE);
	glFrontFace(GL_CW);
	glCullFace(GL_BACK);

	glEnable(GL_DEPTH_TEST); 

	this->inicializar();

	glutKeyboardFunc(ouvirTecladoCallback);
	glutSpecialFunc(ouvirTecladoEspecialCallback);
	glutPassiveMotionFunc(mousePassivoCallback);
	temporizador = high_resolution_clock::now();
}

OpenGLContext::~OpenGLContext() {
	this->finalize();
}

void OpenGLContext::inicializar() {

	// Seta a "limpeza" ou a cor de fundo
	glClearColor(0.2f, 0.2f, 0.2f, 1.0f); // Preto e opaco

	string pathShaderFrag;

	if (opcaoShader == 0) { // Padrão inicial da primeira etapa
		pathShaderFrag = "shader/trabalho.frag";

	} else if (opcaoShader == 1) { // Ambiente
		pathShaderFrag = "shader/ambiente.frag";

	} else if (opcaoShader == 2) { // Difusa
		pathShaderFrag = "shader/difusa.frag";

	} else if (opcaoShader == 3) { // Especular
		pathShaderFrag = "shader/especular.frag";
	
	} else if (opcaoShader == 4) { // Todas juntas
		pathShaderFrag = "shader/juncao.frag";

	}

	// Cria e compila o nosso programa GLSL a partir dos shaders
	GLint vertexShaderId = this->loadAndCompileShader("shader/trabalho.vert", GL_VERTEX_SHADER);
	GLint fragmentShaderId = this->loadAndCompileShader(pathShaderFrag, GL_FRAGMENT_SHADER);
	this->programId = this->linkShaderProgram(vertexShaderId, fragmentShaderId);

	criarBufferDosIndices();
	criarBufferDosVertex();

}

void OpenGLContext::glutReshapeCallback(int width, int height) {
	// Define a viewport para cobrir a nova tela
	glViewport(0, 0, width, height);
}

void OpenGLContext::glutRenderCallback() {
	currentInstance->renderizar();
}

void OpenGLContext::renderizar() {
	
	// Limpando o buffer de cor
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	// Carregando tudo de volta
	glUseProgram(this->programId);
	glBindVertexArray(this->VAOId);
	glBindBuffer(GL_ARRAY_BUFFER, this->VBOId);

	// Transformacoes...
	camera.renderizacao(); // Movimento de camera manual
	this->rotacionar();
	this->efeitos();
	this->moverCamera(); // Movimento pre-setado
	this->iluminacao();

	glDepthFunc(GL_LESS);

	// Desenhando...
	glDrawElements(GL_TRIANGLES, 78, GL_UNSIGNED_INT, 0);

	// Limpando as coisas
	glBindBuffer(GL_ARRAY_BUFFER, 0);
	glBindVertexArray(0);
	glUseProgram(0);

	glutSwapBuffers(); //! Necessario para windows!
}

void OpenGLContext::rotacionar() {
	anguloRotacao = (anguloRotacao + 1) % 360;

	double radianos = paraRadianos(anguloRotacao);

	Matriz4f rotacao (cosf(radianos), 0.0f, -sinf(radianos), 0.0f,
					  0.0f		    , 1.0f,  0.0f		   , 0.0f,
					  sinf(radianos), 0.0f,  cosf(radianos), 0.0f,
					  0.0f          , 0.0f,  0.0f		   , 1.0f );

	Matriz4f translacao (1.0f, 0.0f, 0.0f, 0.0f,
						 0.0f, 1.0f, 0.0f, 0.0f,
						 0.0f, 0.0f, 1.0f, 2.0f,
						 0.0f, 0.0f, 0.0f, 1.0f );
	
	Matriz4f mundo = translacao * rotacao;

	Matriz4f visao = camera.getMatriz();

	GLfloat fov = 90.0f;
	GLfloat tangenteMetadeFov = tanf(paraRadianos(fov / 2.0f));
	GLfloat xyProjecao = 1 / tangenteMetadeFov; 
	GLfloat aspectRatio = (float)GLUT_WINDOW_WIDTH / (float)GLUT_WINDOW_HEIGHT;

	GLfloat xyAS = xyProjecao / aspectRatio;

	GLfloat zPerto = 1.0f;
	GLfloat zLonge = 10.0f;
	GLfloat zRange = zPerto - zLonge;

	GLfloat A = (-zLonge - zPerto) / zRange;
	GLfloat B = 2.0f * zLonge * zPerto / zRange;

	Matriz4f projecao ( xyAS, 0.0f		, 0.0f, 0.0f,
						0.0f, xyProjecao, 0.0f, 0.0f,
						0.0f, 0.0f		, A   , B	,
						0.0f, 0.0f		, 1.0f, 0.0f );

	Matriz4f matrizFinal = projecao * visao * mundo;
	Matriz4f mundoSemRotacao = projecao * visao * translacao;
	Matriz4f projecaoEVisao = projecao * visao;

	GLint ponteiroParaMundo = glGetUniformLocation(this->programId, "mundo");
	GLint ponteiroParaMundoSemRotacao = glGetUniformLocation(this->programId, "mundoSemRotacao");
	GLint ponteiroProjecaoEVisao = glGetUniformLocation(this->programId, "projecaoEVisao");
	GLint ponteiroCamera = glGetUniformLocation(this->programId, "cameraPos");

	glUniformMatrix4fv(ponteiroParaMundo, 1, GL_TRUE, matrizFinal);
	glUniformMatrix4fv(ponteiroParaMundoSemRotacao, 1, GL_TRUE, mundoSemRotacao);
	glUniformMatrix4fv(ponteiroProjecaoEVisao, 1, GL_TRUE, projecaoEVisao);
	glUniform3fv(ponteiroCamera, 1, camera.getVetPosicao());

}

void OpenGLContext::efeitos() {
	//wAlternado = wAlternado + 0.01f;
	wAlternado = fmod(wAlternado, 5.0f);

	wAlternado = wAlternado < 1.0f ? 1.0f : wAlternado;

	GLint ponteiroParaWAlternado = glGetUniformLocation(this->programId, "wAlternado");
	glUniform1f(ponteiroParaWAlternado, wAlternado);
}

void OpenGLContext::moverCamera() {
	auto agora = high_resolution_clock::now();

	switch (estadoCamera) {
	case 0: // Estado inicial, apos os segundos setados ele comeca a subir
		if (duration_cast<seconds>(agora - temporizador).count() >= segundosEsperaAnimacao) {
			camera.setVelocidade(0.075);
			camera.rotacionarCameraBaixo();
			camera.moverCameraParaCima();
			camera.moverCameraParaFrente();
			if (camera.getAnguloVertical() == (camera.getAnguloVerticalOriginal() + 48)) {
				temporizador = agora;
				estadoCamera = 1;
			}
		}
		break;
	
	case 1: // Termina de subir 
		camera.rotacionarCameraBaixo();
		if (camera.getAnguloVertical() == (camera.getAnguloVerticalOriginal() + 90)) {
			temporizador = agora;
			estadoCamera = 2;
		}
		break;

	case 2: // Em cima, comeca a abaixar em 5 segs
		if (duration_cast<seconds>(agora - temporizador).count() >= segundosEsperaAnimacao) {
			camera.rotacionarCameraCima();
			if (camera.getAnguloVertical() == (camera.getAnguloVerticalOriginal() + 48)) {
				temporizador = agora;
				estadoCamera = 3;
			}
		}
		break;

	case 3: // Terminar de abaixar, termina abaixaido
		camera.rotacionarCameraCima();
		camera.moverCameraParaBaixo();
		camera.moverCameraParaAtras();
		if (camera.getAnguloVertical() == (camera.getAnguloVerticalOriginal())) {
			camera.setVetPosicao(cameraPosOriginal);
			temporizador = agora;
			estadoCamera = 4;
		}
		break;
	
	case 4: // Origem esperando 5 segs, depois comeca a descer
		if (duration_cast<seconds>(agora - temporizador).count() >= segundosEsperaAnimacao) {
			camera.rotacionarCameraCima();
			camera.moverCameraParaBaixo();
			camera.moverCameraParaFrente();
			if (camera.getAnguloVertical() == (camera.getAnguloVerticalOriginal() + 312)) {
				temporizador = agora;
				estadoCamera = 5;
			}
		}
		break;

	case 5: // Termina de descer
		camera.rotacionarCameraCima();
		if (camera.getAnguloVertical() == (camera.getAnguloVerticalOriginal() + 270)) {
			temporizador = agora;
			estadoCamera = 6;
		}
		break;
	
	case 6: // Em baixo, volta a subir em 5 segundos
		if (duration_cast<seconds>(agora - temporizador).count() >= segundosEsperaAnimacao) {
			camera.rotacionarCameraBaixo();
			if (camera.getAnguloVertical() == (camera.getAnguloVerticalOriginal() + 312)) {
				temporizador = agora;
				estadoCamera = 7;
			}
		}
		break;
	
	case 7: // Termina de suibr
		camera.rotacionarCameraBaixo();
		camera.moverCameraParaCima();
		camera.moverCameraParaAtras();
		if (camera.getAnguloVertical() == camera.getAnguloVerticalOriginal()) {
			camera.setVetPosicao(cameraPosOriginal);
			temporizador = agora;
			estadoCamera = 0;
		}
		break;

	default:
		break;

	}
}

void OpenGLContext::iluminacao() {

	// Configuração da iluminação
	float kAmbiente = 0.23125;
	float kDifuso = 0.5;
	float kEspecular = 0.773911;
	float alpha = 89.6;

	if (opcaoShader >= 1) { // Ambiente
		GLint ponteiroKAmbiente = glGetUniformLocation(this->programId, "kA");
		glUniform1f(ponteiroKAmbiente, kAmbiente);

	} if (opcaoShader >= 2) { // Difusa
		GLint ponteiroKDifuso = glGetUniformLocation(this->programId, "kD");
		glUniform1f(ponteiroKDifuso, kDifuso);

	} if (opcaoShader >= 3) { // Especular
		GLint ponteiroKEspecular = glGetUniformLocation(this->programId, "kS");
		GLint ponteiroAlpha = glGetUniformLocation(this->programId, "alpha");

		glUniform1f(ponteiroKEspecular, kEspecular);
		glUniform1f(ponteiroAlpha, alpha);
	}

}

void OpenGLContext::criarBufferDosVertex() {
	GLfloat vermelho[3] = {1.000, 0.000, 0.000}; // Vermelho
	GLfloat verde[3]    = {0.000, 1.000, 0.000}; // Verde
	GLfloat azul[3]     = {0.000, 0.000, 1.000}; // Azul
	GLfloat roxo[3]     = {0.741, 0.043, 0.741}; // Roxo
	GLfloat rosa[3] 	= {1.000, 0.000, 0.400}; // Rosa
	GLfloat	ciano[3] 	= {0.255, 0.718, 1.000}; // Ciano
	GLfloat branco[3]   = {1.000, 1.000, 1.000}; // Branco

	GLfloat verdeAlt[3]   = {0.190, 0.570, 0.430}; 
	GLfloat azulEscuro[3] = {0.020, 0.480, 0.760}; 
	GLfloat azulClaro[3]  = {0.510, 0.730, 0.850}; 
	GLfloat amarelo[3] 	  = {0.920, 0.760, 0.000}; 
	GLfloat laranja[3]    = {0.950, 0.460, 0.280}; 
	GLfloat bordo[3]      = {0.830, 0.370, 0.380};

	GLfloat preto[3]       = {0.000, 0.000, 0.000};
	GLfloat azulAlt[3]	   = {0.007, 0.016, 0.980};
	GLfloat lima[3] 	   = {0.007, 0.980, 0.336};
	GLfloat vermelhoAlt[3] = {0.980, 0.007, 0.086};
	GLfloat amareloAlt[3]  = {0.980, 0.780, 0.062};

	//* Comeco da piramide
	vertices[0] = Vertex(-0.7,  0.15, 0.0, branco);

	vertices[1] = Vertex(-0.8, -0.1,  0.1, vermelho); // A
	vertices[2] = Vertex(-0.6, -0.1,  0.1, vermelho); // B
	
	vertices[3] = Vertex(-0.6, -0.1, -0.1, verde); // D
	vertices[4] = Vertex(-0.8, -0.1, -0.1, verde); // C
	
	vertices[5] = Vertex(-0.6, -0.1,  0.1, azul); // B
	vertices[6] = Vertex(-0.6, -0.1, -0.1, azul); // D
	
	vertices[7] = Vertex(-0.8, -0.1, -0.1, roxo); // C
	vertices[8] = Vertex(-0.8, -0.1,  0.1, roxo); // A
	
	vertices[9]  = Vertex(-0.8, -0.1,  0.1, ciano); // A
	vertices[10] = Vertex(-0.6, -0.1,  0.1, rosa); // B
	vertices[11] = Vertex(-0.8, -0.1, -0.1, rosa); // C
	vertices[12] = Vertex(-0.6, -0.1, -0.1, ciano); // D
	//* Fim da piramide

	//* Comeco do cubo
	//* Primeiro lado
	// 9
	vertices[13]  = Vertex( 0.1,  0.1,  0.1, verdeAlt); // A
	vertices[14] = Vertex(-0.1,  0.1,  0.1, verdeAlt); // B
	vertices[15] = Vertex( 0.1, -0.1,  0.1, verdeAlt); // C
	vertices[16] = Vertex(-0.1,  0.1,  0.1, verdeAlt); // B
	vertices[17] = Vertex(-0.1, -0.1,  0.1, verdeAlt); // D
	vertices[18] = Vertex( 0.1, -0.1,  0.1, verdeAlt); // C

	//* Segundo lado
	vertices[19] = Vertex( 0.1,  0.1, -0.1, azulEscuro); // E
	vertices[20] = Vertex(-0.1,  0.1, -0.1, azulEscuro); // F
	vertices[21] = Vertex( 0.1, -0.1, -0.1, azulEscuro); // G
	vertices[22] = Vertex(-0.1,  0.1, -0.1, azulEscuro); // F
	vertices[23] = Vertex(-0.1, -0.1, -0.1, azulEscuro); // H
	vertices[24] = Vertex( 0.1, -0.1, -0.1, azulEscuro); // G

	//* Terceiro lado
	vertices[25] = Vertex(-0.1,  0.1,  0.1, azulClaro); // B
	vertices[26] = Vertex(-0.1,  0.1, -0.1, azulClaro); // F
	vertices[27] = Vertex(-0.1, -0.1,  0.1, azulClaro); // D
	vertices[28] = Vertex(-0.1,  0.1, -0.1, azulClaro); // F
	vertices[29] = Vertex(-0.1, -0.1, -0.1, azulClaro); // H
	vertices[30] = Vertex(-0.1, -0.1,  0.1, azulClaro); // D

	//* Quarto lado
	vertices[31] = Vertex(-0.1, -0.1,  0.1, amarelo); // D
	vertices[32] = Vertex(-0.1, -0.1, -0.1, amarelo); // H
	vertices[33] = Vertex( 0.1, -0.1,  0.1, amarelo); // C
	vertices[34] = Vertex(-0.1, -0.1, -0.1, amarelo); // H
	vertices[35] = Vertex( 0.1, -0.1, -0.1, amarelo); // G
	vertices[36] = Vertex( 0.1, -0.1,  0.1, amarelo); // C

	//* Quinto lado
	vertices[37] = Vertex( 0.1, -0.1,  0.1, laranja); // C
	vertices[38] = Vertex( 0.1, -0.1, -0.1, laranja); // G
	vertices[39] = Vertex( 0.1,  0.1,  0.1, laranja); // A
	vertices[40] = Vertex( 0.1, -0.1, -0.1, laranja); // G
	vertices[41] = Vertex( 0.1,  0.1, -0.1, laranja); // E
	vertices[42] = Vertex( 0.1,  0.1,  0.1, laranja); // A

	//* Sexto lado
	vertices[43] = Vertex( 0.1,  0.1,  0.1, bordo); // A
	vertices[44] = Vertex( 0.1,  0.1, -0.1, bordo); // E
	vertices[45] = Vertex(-0.1,  0.1,  0.1, bordo); // B
	vertices[46] = Vertex( 0.1,  0.1, -0.1, bordo); // E
	vertices[47] = Vertex(-0.1,  0.1, -0.1, bordo); // F
	vertices[48] = Vertex(-0.1,  0.1,  0.1, bordo); // B
	//* Fim do cubo

	//* Inicio do octaedro
	vertices[49] = Vertex( 0.7, 0.4, 0.0, branco); // Topo
	
	vertices[50] = Vertex( 0.6,  0.0,  0.0, vermelhoAlt); // A
	vertices[51] = Vertex( 0.8,  0.0,  0.0, lima); 	 	  // B
	vertices[52] = Vertex( 0.7,  0.0,  0.15, azulAlt);    // C 
	vertices[53] = Vertex( 0.7,  0.0, -0.15, amareloAlt); // D
	
	vertices[54] = Vertex( 0.7, -0.4, 0.0, preto); // Baixo
	//* Fim do octaedro

	criarBufferDasNormais();

	glGenBuffers(1, &VBOId);
	glBindBuffer(GL_ARRAY_BUFFER, VBOId);
	glBufferData(GL_ARRAY_BUFFER, sizeof(vertices), vertices, GL_STATIC_DRAW);

	glEnableVertexAttribArray(0); // Coordenadas
	glVertexAttribPointer(0, 3, GL_FLOAT, GL_FALSE, 9 * sizeof(GLfloat), 0);

	glEnableVertexAttribArray(1); // RGB
	glVertexAttribPointer(1, 3, GL_FLOAT, GL_FALSE, 9 * sizeof(GLfloat), (GLvoid*)(3 * sizeof(GLfloat)));

	glEnableVertexAttribArray(2); // Normal
	glVertexAttribPointer(2, 3, GL_FLOAT, GL_FALSE, 9 * sizeof(GLfloat), (GLvoid*)(6 * sizeof(GLfloat)));

}

void OpenGLContext::criarBufferDosIndices() {
	unsigned int indicesAux[] = {
		//* Inicio da piramida
		0, 1, 2, // 3
		0, 3, 4, // 6
		0, 5, 6, // 9
		0, 7, 8, // 12
		9, 11, 10, // 15
		11, 12, 10, // 18

		//* Inicio do cubo
		
		13, 14, 15, // 21
		16, 17, 18, // 24
		
		21, 20, 19, // 27
		24, 23, 22, // 30
		
		25, 26, 27, // 33
		28, 29, 30, // 36
		
		37, 38, 39, // 39
		40, 41, 42, // 42

		43, 44, 45, // 45
		46, 47, 48, // 48

		31, 32, 33, // 51
		34, 35, 36, // 54

		//* Inicio do octaedro
		49, 50, 52, // 57
		49, 53, 50, // 60
		49, 52, 51, // 63
		49, 51, 53, // 66

		54, 52, 50, // 69
		54, 50, 53, // 72
		54, 51, 52, // 75
		54, 53, 51, // 78
	};

	for(int i = 0; i < 78; i++) {
		indices[i] = indicesAux[i];
	}

	glGenBuffers(1, &IBOId);
	glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, IBOId);
	glBufferData(GL_ELEMENT_ARRAY_BUFFER, sizeof(indices), indices, GL_STATIC_DRAW);

}

void OpenGLContext::criarBufferDasNormais() {
	vector<int> qtdUsosDoVertice;

    // initialize vertex normals to 0
    for (int i = 0; i != 55; i++) {
        vetorNormal[i] = Vetor3f(0.0f, 0.0f, 0.0f);
		qtdUsosDoVertice.push_back(0);
    }

    // For each face calculate normals and append to the corresponding vertices of the face
    for (unsigned int i = 0; i < 78; i += 3) {
        //vi v(i+1) v(i+2) are the three faces of a triangle
        Vetor3f A = vertices[indices[i]].getVertices();
        Vetor3f B = vertices[indices[i + 1]].getVertices();
        Vetor3f C = vertices[indices[i + 2]].getVertices();
		// printf("A: x = %.3f, y = %.3f, z = %.3f\n", A.x, A.y, A.z);
		// printf("B: x = %.3f, y = %.3f, z = %.3f\n", B.x, B.y, B.z);
		// printf("C: x = %.3f, y = %.3f, z = %.3f\n", C.x, C.y, C.z);

        Vetor3f AB = B - A;
        Vetor3f AC = C - A;
		
        Vetor3f ABxAC = AB.produtoVetorial(AC);
		// printf("ABxAC: x = %.3f, y = %.3f, z = %.3f\n", ABxAC.x, ABxAC.y, ABxAC.z);

        vetorNormal[indices[i]] += ABxAC;
        vetorNormal[indices[i + 1]] += ABxAC;
        vetorNormal[indices[i + 2]] += ABxAC;

		//Feito s * n
		//Falta dividir o resultado por ||s|| e ||n||

		qtdUsosDoVertice[indices[i]] += 1;
		qtdUsosDoVertice[indices[i + 1]] += 1;
		qtdUsosDoVertice[indices[i + 2]] += 1;
		// cout << "i: " << i << endl;
		// printf("vetN: x = %.3f, y = %.3f, z = %.3f\n", vetorNormal[i].x, vetorNormal[i].y, vetorNormal[i].z);
		// printf("vetN: x = %.3f, y = %.3f, z = %.3f\n", vetorNormal[i + 1].x, vetorNormal[i + 1].y, vetorNormal[i + 1].z);
		// printf("vetN: x = %.3f, y = %.3f, z = %.3f\n", vetorNormal[i + 2].x, vetorNormal[i + 2].y, vetorNormal[i + 2].z);
    }

	for (int i = 0; i < 55; i++) {
		vetorNormal[i] = vetorNormal[i] / qtdUsosDoVertice[i];

		vertices[i].adicionarNormal(vetorNormal[i].x, vetorNormal[i].y, vetorNormal[i].z);
		//cout << vetorNormal[i].x << " " << vetorNormal[i].y << " " << vetorNormal[i].z << endl;
	}

}

void OpenGLContext::printVersion() const {
	string glRender    = reinterpret_cast<char const*>(glGetString(GL_RENDERER));
	string glVersion   = reinterpret_cast<char const*>(glGetString(GL_VERSION));
	string glslVersion = reinterpret_cast<char const*>(glGetString(GL_SHADING_LANGUAGE_VERSION));

	cout << "OpenGL Renderer  : " << glRender 	 << endl
		 << "OpenGL Version   : " << glVersion 	 << endl
		 << "OpenGLSL Version : " << glslVersion << endl;
}

void OpenGLContext::rodarLoop() {
	while (true) {
		this->renderizar();
		glutMainLoopEvent();
	}
}

void OpenGLContext::finalize() const {
	
	// Desaloca, apropriadamente, todos os recursos depois que eles ja cumpriram seu papel
	glDisableVertexAttribArray(0);
	glDisableVertexAttribArray(1);
	glDeleteVertexArrays(1, &(this->VAOId));
	glDeleteBuffers(1, &(this->VBOId));
	glUseProgram(0);
}

unsigned int OpenGLContext::loadAndCompileShader(const string &filename, const int glType) const {
	string shaderCode;

	// Le o codigo do Vertex shader do arquivo fonte
	{
		// Abre um ponteiro de leitura
		ifstream shaderStream(filename, ios::in);

		// Verifica se ocorreu algum erro durante a leitura do arquivo
		if (shaderStream.good() == false) {
			throw runtime_error("Erro ao abrir " + filename + ". Are you in the right directory ?");
		}

		// Copia as informacoes do arquivo
		shaderCode.assign((istreambuf_iterator<char>(shaderStream)), (istreambuf_iterator<char>()));

	} // Fim do escopo, RAII shaderStream fechado

	// Algumas variaveis temporarias para o codigo a frente
	char const * sourcePointer = shaderCode.c_str(); // Ponteiro para uma string
	GLint result = GL_FALSE; // O resultado do retorno as chamadas da API do OpenGL
	int infoLogLength = 0; // Tamanho do infoLog
	
	// Cria um shader na GPU e retorna o seu ID
	unsigned int shaderId = glCreateShader(glType);

	// Compila o shaders
	cout << "Compilando o shader: " << filename << endl;

	glShaderSource(shaderId, 1, &sourcePointer, nullptr);
	glCompileShader(shaderId);

	// Verifica se o shaders possui algum erro
	glGetShaderiv(shaderId, GL_COMPILE_STATUS, &result);
	glGetShaderiv(shaderId, GL_INFO_LOG_LENGTH, &infoLogLength);

	// Caso aconteca algum erro
	if (result == GL_FALSE) {
		// Aloca um vetor de char para a mensagem de erro
		vector<char> errorMessage(infoLogLength + 1);

		// Copia a mensagem de erro para uma String
		glGetShaderInfoLog(shaderId, infoLogLength, nullptr, &errorMessage[0]);

		// Printa a mensagem de erro
		cerr << "Erro durante a compilacao " << filename << "\n  " << string(errorMessage.begin(), errorMessage.end()) << endl;
	}

	return shaderId;
}

unsigned int OpenGLContext::linkShaderProgram(unsigned int vertexShaderId, unsigned int fragmentShaderId) const {
	
	// Cria um programa de Shader na GPU e retorna o id do programa
	unsigned int shaderProgramId = glCreateProgram();

	// Acopla o shaders para linka-los posteriormente
	glAttachShader(shaderProgramId, vertexShaderId);
	glAttachShader(shaderProgramId, fragmentShaderId);

	//! Setup antigo
	// Setup Vertex Attributes (only for GL < 3.3 and GLSL < 3.3)
	// glBindAttribLocation (this->getId(), 0, "vertexPosition_modelspace");
	//! Fim de setup antigo

	// Linka o programa
	glLinkProgram(shaderProgramId);

	// Algumas variaveis temporarias para o codigo a frente
	GLint result = GL_FALSE;
	int InfoLogLength = 0;

	// Verifica o programa, certificando de que nao ha erros
	glGetProgramiv(shaderProgramId, GL_LINK_STATUS, &result);
	glGetProgramiv(shaderProgramId, GL_INFO_LOG_LENGTH, &InfoLogLength);

	// No caso de algum erro
	if (result == GL_FALSE) {

		// Aloca um vetor de char para a mensagem de erro
		vector<char> errorMessage(InfoLogLength + 1);

		// Copia mensagem de erro para uma string
		glGetProgramInfoLog(shaderProgramId, InfoLogLength, nullptr, &errorMessage[0]);

		// Printa a mensagem de erro
		cerr << string(errorMessage.begin(), errorMessage.end()) << endl;
	}

	// Desacoplando
	glDetachShader(shaderProgramId, vertexShaderId);
	glDetachShader(shaderProgramId, fragmentShaderId);

	// E por fim deleta o codigo fonte do shaders
	glDeleteShader(vertexShaderId);
	glDeleteShader(fragmentShaderId);

	return shaderProgramId;
}

int main(int argc, char *argv[]) {

	int opcao = -1;

	cout << "--- Bem vindo! ---" << endl;
	while (opcao < 0 || opcao > 4) {
		cout << "Escolha o tipo de iluminacao:" << endl;
		cout << "[0] Sem efeito de iluminacao" << endl;
		cout << "[1] Ambiente" << endl;
		cout << "[2] Difusa" << endl;
		cout << "[3] Especular" << endl;
		cout << "[4] Todas juntas" << endl;
		cout << "------------------" << endl;
		cin >> opcao;
	}

	OpenGLContext context { argc, argv, opcao };

	context.printVersion();
	context.rodarLoop();

	return 0;
}
