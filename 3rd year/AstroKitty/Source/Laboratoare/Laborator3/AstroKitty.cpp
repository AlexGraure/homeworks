//MARIUS-ALEXANDRU GRAURE 335CB
#include "AstroKitty.h"

#include <vector>
#include <iostream>

#include <Core/Engine.h>
#include "Transform2D.h"
#include "Object2D.h"

using namespace std;

AstroKitty::AstroKitty()
{
}

AstroKitty::~AstroKitty()
{
}

void AstroKitty::Init()
{
	glm::ivec2 resolution = window->GetResolution();
	auto camera = GetSceneCamera();
	camera->SetOrthographic(0, (float)resolution.x, 0, (float)resolution.y, 0.01f, 400);
	camera->SetPosition(glm::vec3(0, 0, 50));
	camera->SetRotation(glm::vec3(0, 0, 0));
	camera->Update();
	GetCameraInput()->SetActive(false);

	// initialize tx and ty (the translation steps)
	translateX = 0.0f;
	translateY = 0.0f;

	// initialize sx and sy (the scale factors)
	scaleX = 1;
	scaleY = 1;

	// initialize angularStep
	angularStep = 0.0f;
	angularStepReverse = 0.0f;

	moveX = 0.0f;
	moveY = 0.0f;
	head = glm::vec3(0.0f, 0.0f, 0.0f);
	astroCenter = glm::vec3(100.0f, 255.0f, 0.0f);
	astroAngularStep = 0.0f;
	length = 0.0f;
	index = 0;
	baseNr = 0;
	refNr = 0;
	asteroNr = 0;
	update = false;
	beginning = true;
	noInput = false;
	stop = false;

	//Create astronaut
	glm::vec3 corner = glm::vec3(0.0f, 0.0f, 0.0f);
	glm::vec3 color = glm::vec3(0.115, 0.5, 2);
	length = 60.0f;
	tRadius = length * sqrt(3) / 3;
	Mesh* astro = Object2D::CreateTriangle("astro", corner, length, color, &head, &astroCenter);
	AddMeshToList(astro);

	//Create platforms

	baseDim[index].x = 10.0f;
	baseDim[index].y = resolution.y;
	baseCorner[index] = glm::vec3(0.0f, 0.0f, 0.0f);
	color = glm::vec3(0.486f, 0.988f, 0.000f);
	Mesh* s1 = Object2D::CreateRectangle("s1", baseCorner[index], baseDim[index], color);
	AddMeshToList(s1);
	baseNr++;
	index++;

	baseDim[index].x = resolution.x - 10.0f;
	baseDim[index].y = 10.0f;
	baseCorner[index] = glm::vec3(10.0f, resolution.y - 10.0f, 0.0f);
	Mesh* s2 = Object2D::CreateRectangle("s2", baseCorner[index], baseDim[index], color);
	AddMeshToList(s2);
	baseNr++;
	index++;

	baseDim[index].x = 10.0f;
	baseDim[index].y = resolution.y - 10.0f;
	baseCorner[index] = glm::vec3(resolution.x - 10.0f, 0.0f, 0.0f);
	Mesh* s3 = Object2D::CreateRectangle("s3", baseCorner[index], baseDim[index], color);
	AddMeshToList(s3);
	baseNr++;
	index++;

	baseDim[index].x = resolution.x - 20.0f;
	baseDim[index].y = 10.0f;
	baseCorner[index] = glm::vec3(10.0f, 0.0f, 0.0f);
	Mesh* s4 = Object2D::CreateRectangle("s4", baseCorner[index], baseDim[index], color);
	AddMeshToList(s4);
	baseNr++;
	index++;

	color = glm::vec3(0.580, 0.000, 0.827);
	baseDim[index].x = 20.0f;
	baseDim[index].y = 320.0f;
	baseCorner[index] = glm::vec3(310.0f, 370.0f, 0.0f);
	Mesh* s5 = Object2D::CreateRectangle("s5", baseCorner[index], baseDim[index], color);
	AddMeshToList(s5);
	baseNr++;
	index++;

	baseDim[index].x = 20.0f;
	baseDim[index].y = 330.0f;
	baseCorner[index] = glm::vec3(560.0f, 110.0f, 0.0f);
	Mesh* s6 = Object2D::CreateRectangle("s6", baseCorner[index], baseDim[index], color);
	AddMeshToList(s6);
	baseNr++;
	index++;

	//Reflex
	color = glm::vec3(0, 1, 1);
	index = 0;

	refDim[index].x = 550.0f;
	refDim[index].y = 20.0f;
	refCorner[index] = glm::vec3(10.0f, 110.0f, 0.0f);
	Mesh* r1 = Object2D::CreateRectangle("r1", refCorner[index], refDim[index], color);
	AddMeshToList(r1);
	refNr++;
	index++;

	refDim[index].x = 300.0f;
	refDim[index].y = 20.0f;
	refCorner[index] = glm::vec3(10.0f, 370.0f, 0.0f);
	Mesh* r2 = Object2D::CreateRectangle("r2", refCorner[index], refDim[index], color);
	AddMeshToList(r2);
	refNr++;
	index++;

	refDim[index].x = resolution.x - 590.0f;
	refDim[index].y = 20.0f;
	refCorner[index] = glm::vec3(580.0f, 420.0f, 0.0f);
	Mesh* r3 = Object2D::CreateRectangle("r3", refCorner[index], refDim[index], color);
	AddMeshToList(r3);
	refNr++;
	index++;

	refDim[index].x = resolution.x - 340.0f;
	refDim[index].y = 20.0f;
	refCorner[index] = glm::vec3(330.0f, 670.0f, 0.0f);
	Mesh* r4 = Object2D::CreateRectangle("r4", refCorner[index], refDim[index], color);
	AddMeshToList(r4);
	refNr++;
	index++;

	//Create final platform
	color = glm::vec3(1, 0, 0);
	finishDim.x = 20.0f;
	finishDim.y = 230.0f;
	finishCorner = glm::vec3(1180.0f, 440.0f, 0.0f);
	Mesh* finish = Object2D::CreateRectangle("finish", finishCorner, finishDim, color);
	AddMeshToList(finish);

	index = 0;
	//Create asteroids
	color = glm::vec3(0.502f, 0.0f, 0.0f);
	/*Ast1*/
	asteroCenter[index].x = 140.0f;
	asteroCenter[index].y = 540.0f;
	length = 5.0f;
	asteroRadius[index] = length * 8;
	Mesh* asteroid1 = Object2D::CreateCircle("asteroid1", asteroCenter[index].x, asteroCenter[index].y, length, color);
	AddMeshToList(asteroid1);
	asteroNr++;
	index++;

	asteroCenter[index].x = 980.0f;
	asteroCenter[index].y = 220.0f;
	length = 9.0f;
	asteroRadius[index] = length * 8;
	Mesh* asteroid2 = Object2D::CreateCircle("asteroid2", asteroCenter[index].x, asteroCenter[index].y, length, color);
	AddMeshToList(asteroid2);
	asteroNr++;
	index++;

	asteroCenter[index].x = 1050.0f;
	asteroCenter[index].y = 560.0f;
	length = 12.0f;
	asteroRadius[index] = length * 8;
	Mesh* asteroid3 = Object2D::CreateCircle("asteroid3", asteroCenter[index].x, asteroCenter[index].y, length, color);
	AddMeshToList(asteroid3);
	asteroNr++;
	index++;
}

void AstroKitty::FrameStart()
{
	// clears the color buffer (using the previously set color) and depth buffer
	glClearColor(0, 0, 0, 1);
	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

	glm::ivec2 resolution = window->GetResolution();
	// sets the screen area where to draw
	glViewport(0, 0, resolution.x, resolution.y);
}

void AstroKitty::Update(float deltaTimeSeconds)
{
	if (stop == true) {
		return;
	}
	modelMatrix = glm::mat3(1);
	if (update == true) {
		astroCenter.x += moveX * deltaTimeSeconds;
		astroCenter.y += moveY * deltaTimeSeconds;
		AstroKitty::collisionWithPlatforms();
	}
	else {
		if (beginning == true)
		{
			astroCenter = glm::vec3(100.0f, 255.0f, 0.0f);
		}
	}

	modelMatrix *= Transform2D::Translate(astroCenter.x, astroCenter.y);
	modelMatrix *= Transform2D::Rotate(astroAngularStep);
	RenderMesh2D(meshes["astro"], shaders["VertexColor"], modelMatrix);


	index = 0;
	modelMatrix = glm::mat3(1);
	RenderMesh2D(meshes["s1"], shaders["VertexColor"], modelMatrix);
	index++;

	modelMatrix = glm::mat3(1);
	RenderMesh2D(meshes["s2"], shaders["VertexColor"], modelMatrix);
	index++;

	modelMatrix = glm::mat3(1);
	RenderMesh2D(meshes["s3"], shaders["VertexColor"], modelMatrix);

	modelMatrix = glm::mat3(1);
	RenderMesh2D(meshes["s4"], shaders["VertexColor"], modelMatrix);
	index++;

	modelMatrix = glm::mat3(1);
	RenderMesh2D(meshes["s5"], shaders["VertexColor"], modelMatrix);

	modelMatrix = glm::mat3(1);
	RenderMesh2D(meshes["s6"], shaders["VertexColor"], modelMatrix);

	index = 0;

	modelMatrix = glm::mat3(1);
	RenderMesh2D(meshes["r1"], shaders["VertexColor"], modelMatrix);
	index++;

	modelMatrix = glm::mat3(1);
	RenderMesh2D(meshes["r2"], shaders["VertexColor"], modelMatrix);
	index++;


	modelMatrix = glm::mat3(1);
	RenderMesh2D(meshes["r3"], shaders["VertexColor"], modelMatrix);
	index++;

	modelMatrix = glm::mat3(1);
	RenderMesh2D(meshes["r4"], shaders["VertexColor"], modelMatrix);
	index++;

	modelMatrix = glm::mat3(1);
	RenderMesh2D(meshes["finish"], shaders["VertexColor"], modelMatrix);


	index = 0;
	if (out[index] == false)
	{
		angularStep += deltaTimeSeconds * 2;
		modelMatrix = glm::mat3(1);
		modelMatrix *= Transform2D::Translate(asteroCenter[index].x + 15, asteroCenter[index].y + 15);
		modelMatrix *= Transform2D::Rotate(angularStep);
		modelMatrix *= Transform2D::Translate(-asteroCenter[index].x - 15, -asteroCenter[index].y - 15);
		RenderMesh2D(meshes["asteroid1"], shaders["VertexColor"], modelMatrix);
	}
	index++;

	angularStepReverse -= deltaTimeSeconds;

	if (out[index] == false)
	{
		scaleX += deltaTimeSeconds * 2;
		scaleY += deltaTimeSeconds * 2;
		modelMatrix = glm::mat3(1);
		modelMatrix *= Transform2D::Translate(asteroCenter[index].x, asteroCenter[index].y);
		modelMatrix *= Transform2D::Scale(abs(sin(scaleX)) + 1, abs(sin(scaleY)) + 1);
		modelMatrix *= Transform2D::Translate(-asteroCenter[index].x, -asteroCenter[index].y);
		modelMatrix *= Transform2D::Translate(asteroCenter[index].x, asteroCenter[index].y);
		modelMatrix *= Transform2D::Rotate(angularStepReverse);
		modelMatrix *= Transform2D::Translate(-asteroCenter[index].x, -asteroCenter[index].y);
		RenderMesh2D(meshes["asteroid2"], shaders["VertexColor"], modelMatrix);
	}
	index++;

	if (out[index] == false)
	{
		modelMatrix = glm::mat3(1);
		RenderMesh2D(meshes["asteroid3"], shaders["VertexColor"], modelMatrix);
	}
}

void AstroKitty::FrameEnd()
{

}

void AstroKitty::OnInputUpdate(float deltaTime, int mods)
{

}

void AstroKitty::OnKeyPress(int key, int mods)
{
	// add key press event
}

void AstroKitty::OnKeyRelease(int key, int mods)
{
	// add key release event
}

void AstroKitty::OnMouseMove(int mouseX, int mouseY, int deltaX, int deltaY)
{
	// add mouse move event
	if (noInput == false)
	{
		astroAngularStep = atan2(720.0f - mouseY - astroCenter.y, mouseX - astroCenter.x) + M_PI / 2;
	}
}

void AstroKitty::OnMouseBtnPress(int mouseX, int mouseY, int button, int mods)
{
	// add mouse button press event
	if (button == GLFW_MOUSE_BUTTON_2 && noInput == false) {
		astroAngularStep = atan2(720 - mouseY - astroCenter.y, mouseX - astroCenter.x) + M_PI / 2;
		moveX = mouseX - astroCenter.x;
		moveY = 720 - mouseY - astroCenter.y;
		update = true;
		beginning = false;
		noInput = true;
	}
}

void AstroKitty::OnMouseBtnRelease(int mouseX, int mouseY, int button, int mods)
{
	// add mouse button release event
}

void AstroKitty::OnMouseScroll(int mouseX, int mouseY, int offsetX, int offsetY)
{
}

void AstroKitty::OnWindowResize(int width, int height)
{
}

void AstroKitty::collisionWithPlatforms()
{
	float xMin, yMin, xMax, yMax, distance;
	for (size_t i = 0; i < baseNr; i++)
	{
		xMin = baseCorner[i].x;
		yMin = baseCorner[i].y;
		xMax = baseCorner[i].x + baseDim[i].x;
		yMax = baseCorner[i].y + baseDim[i].y;
		distance = AstroKitty::getDistance(xMin, yMin, xMax, yMax);
		if (distance < (tRadius * tRadius))
		{
			std::string newCol = "s" + std::to_string(i + 1);
			if (newCol == collisionName)
			{
				return;
			}
			moveX = 0;
			moveY = 0;
			noInput = false;
			collisionName = newCol;

			if (astroCenter.x > xMax)
			{
				astroAngularStep = RADIANS(90);
				astroCenter.x -= tRadius / 2.3;
			}

			if (astroCenter.x < xMin)
			{
				astroAngularStep = RADIANS(-90);
				astroCenter.x += tRadius / 2.3;
			}

			if (astroCenter.y < yMin)
			{
				astroAngularStep = RADIANS(0);
				astroCenter.y += tRadius / 2.3;
			}

			if (astroCenter.y > yMax)
			{
				astroAngularStep = RADIANS(180);
				astroCenter.y -= tRadius / 2.3;
			}
		}
	}


	for (size_t i = 0; i < refNr; i++)
	{
		xMin = refCorner[i].x;
		yMin = refCorner[i].y;
		xMax = refCorner[i].x + refDim[i].x;
		yMax = refCorner[i].y + refDim[i].y;
		distance = AstroKitty::getDistance(xMin, yMin, xMax, yMax);
		if (distance < (tRadius * tRadius))
		{
			std::string newCol = "r" + std::to_string(i + 1);
			if (newCol == collisionName)
			{
				return;
			}
			collisionName = newCol;
			astroAngularStep = RADIANS(180.0f) - astroAngularStep;
			moveY = -moveY;
		}
	}

	for (size_t i = 0; i < asteroNr; i++)
	{
		distance = sqrt(pow(astroCenter.x - asteroCenter[i].x, 2) + pow(astroCenter.y - asteroCenter[i].y, 2));
		if (distance < tRadius + asteroRadius[i])
		{
			std::string newCol = "astero" + std::to_string(i + 1);
			collisionName = newCol;
			asteroCenter[i].x = 1280;
			asteroCenter[i].y = 720;
			asteroRadius[i] = 0;
			out[i] = true;
			float angleBetween = atan2(astroCenter.y - asteroCenter[i].y, astroCenter.x - asteroCenter[i].x);
			astroAngularStep = 2 * angleBetween - astroAngularStep - RADIANS(90);
			float value = 0.03f;
			moveX /= cos(astroAngularStep - RADIANS(90));
			moveY *= sin(astroAngularStep - RADIANS(90));
		}
	}

	xMin = finishCorner.x;
	yMin = finishCorner.y;
	xMax = finishCorner.x + finishDim.x;
	yMax = finishCorner.y + finishDim.y;
	distance = AstroKitty::getDistance(xMin, yMin, xMax, yMax);
	if (distance < (tRadius * tRadius))
	{
		stop = true;
	}

}

float AstroKitty::getDistance(float xMin, float yMin, float xMax, float yMax)
{
	cX = closestCoord(astroCenter.x, xMin, xMax);
	cY = closestCoord(astroCenter.y, yMin, yMax);

	float dX = astroCenter.x - cX;
	float dY = astroCenter.y - cY;

	return (dX * dX) + (dY * dY);
}

float AstroKitty::closestCoord(float circleCoord, float minRect, float maxRect)
{
	return max(minRect, min(circleCoord, maxRect));
}