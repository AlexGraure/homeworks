//MARIUS-ALEXANDRU GRAURE 335CB
#pragma once

#include <string>

#include <include/glm.h>
#include <Core/GPU/Mesh.h>

namespace Object2D
{

	Mesh * CreateTriangle(std::string name, glm::vec3 leftBottomCorner, float length, glm::vec3 color, glm::vec3 *head, glm::vec3 *astroCenter, bool fill = true);
	Mesh * CreateRectangle(std::string name, glm::vec3 leftBottomCorner, glm::vec2 dimensions, glm::vec3 color, bool fill = true);
	Mesh * CreateCircle(std::string name, float x, float y, float r, glm::vec3 color, bool fill = true);

}

