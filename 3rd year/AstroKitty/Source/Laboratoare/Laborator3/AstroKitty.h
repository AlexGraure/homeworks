//MARIUS-ALEXANDRU GRAURE 335CB
#pragma once

#include <Component/SimpleScene.h>
#include <string>
#include <Core/Engine.h>
#include "Transform2D.h"
#include "Object2D.h"

class AstroKitty : public SimpleScene
{
	public:
		AstroKitty();
		~AstroKitty();

		void Init() override;

	private:
		void FrameStart() override;
		void Update(float deltaTimeSeconds) override;
		void FrameEnd() override;

		void OnInputUpdate(float deltaTime, int mods) override;
		void OnKeyPress(int key, int mods) override;
		void OnKeyRelease(int key, int mods) override;
		void OnMouseMove(int mouseX, int mouseY, int deltaX, int deltaY) override;
		void OnMouseBtnPress(int mouseX, int mouseY, int button, int mods) override;
		void OnMouseBtnRelease(int mouseX, int mouseY, int button, int mods) override;
		void OnMouseScroll(int mouseX, int mouseY, int offsetX, int offsetY) override;
		void OnWindowResize(int width, int height) override;

	protected:
		glm::mat3 modelMatrix;
		float translateX, translateY;
		float scaleX, scaleY;
		float angularStep;
		float length;
		float angularStepReverse;
		float moveX, moveY;
		float tRadius; //raza cercului circumscris triunghiului
		float cX, cY; //cel mai apropiat X si cel mai apropiat Y

		glm::vec3 head; //varful triunghiului
		glm::vec3 astroCenter; //centrul triunghiului
		float astroAngularStep; //unghiul cu care se invarte triunghiul

		/* Datele platforlor de stationare */
		glm::vec3 baseCorner[10];
		glm::vec2 baseDim[10];
		int baseNr;

		/* Datele platformelor de reflexie */
		glm::vec3 refCorner[10];
		glm::vec2 refDim[10];
		int refNr;

		/* Datele asteroizilor */
		glm::vec2 asteroCenter[5];
		int asteroRadius[5];
		int asteroNr;

		/* Datele platformei finale */
		glm::vec3 finishCorner;
		glm::vec2 finishDim;

		int index;
		bool update, beginning, noInput, stop, split;
		bool out[5];
		std::string collisionName;


		void AstroKitty::collisionWithPlatforms();
		float AstroKitty::getDistance(float xMin, float yMin, float xMax, float yMax);
		float AstroKitty::closestCoord(float circleCoord, float min, float max);
};
