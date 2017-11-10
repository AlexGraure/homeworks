//MARIUS-ALEXANDRU GRAURE 335CB
#include "Object2D.h"
#include <Core/Engine.h>

Mesh* Object2D::CreateTriangle(std::string name, glm::vec3 leftBottomCorner, float length, glm::vec3 color, glm::vec3 *head, glm::vec3 *astroCenter, bool fill)
{
	glm::vec3 corner = leftBottomCorner;
	glm::vec3 headColor = glm::vec3(1, 0, 0);
	(*astroCenter).x = 0;
	(*astroCenter).y = 0;

	glm::vec3 left = corner + glm::vec3(-length / 2, (length * sqrt(3)) / 6, 0);
	glm::vec3 right = corner + glm::vec3(length / 2, (length * sqrt(3)) / 6, 0);
	(*head) = corner + glm::vec3(0, (-length * sqrt(3)) / 3, 0);

	std::vector<VertexFormat> vertices =
	{
		VertexFormat(left, glm::vec3(0, 0, 1)),
		VertexFormat(right, glm::vec3(0, 0, 1)),
		VertexFormat(*head, glm::vec3(1, 0, 0)),
		
	};

	Mesh* triangle = new Mesh(name);
	std::vector<unsigned short> indices = { 0, 1, 2};

	if (!fill) {
		triangle->SetDrawMode(GL_LINE_LOOP);
	}
	else {
		indices.push_back(0);
	}

	triangle->InitFromData(vertices, indices);
	return triangle;
}

Mesh* Object2D::CreateRectangle(std::string name, glm::vec3 leftBottomCorner, glm::vec2 dimensions, glm::vec3 color, bool fill)
{
	glm::vec3 corner = leftBottomCorner;
	float length1 = dimensions.x;
	float length2 = dimensions.y;

	glm::vec3 a = corner;
	glm::vec3 b = corner + glm::vec3(length1, 0, 0);
	glm::vec3 c = corner + glm::vec3(length1, length2, 0);
	glm::vec3 d = corner + glm::vec3(0, length2, 0);

	std::vector<VertexFormat> vertices =
	{
		VertexFormat(a, color),
		VertexFormat(b, color),
		VertexFormat(c, color),
		VertexFormat(d, color)
	};

	Mesh* platform = new Mesh(name);
	std::vector<unsigned short> indices = { 0, 1, 2, 3};

	if (!fill) {
		platform->SetDrawMode(GL_LINE_LOOP);
	}
	else {
		indices.push_back(0);
		indices.push_back(2);
	}

	platform->InitFromData(vertices, indices);
	return platform;
}


Mesh* Object2D::CreateCircle(std::string name, float cX, float cY, float r, glm::vec3 color, bool fill)
{
	glm::vec3 center = glm::vec3(cX, cY, 0);
	std::vector<VertexFormat> vertices =
	{
		VertexFormat(center, color), /* 0 */
		VertexFormat(center + glm::vec3(0, 8 * r, 0.0f), color),/* 1 */
		VertexFormat(center + glm::vec3(4 * r, 7 * r, 0.0f), color), /* 2 */
		VertexFormat(center + glm::vec3(7 * r, 4 * r, 0.0f), color), /* 3 */
		VertexFormat(center + glm::vec3(8 * r, 0.0f, 0.0f), color), /* 4 */
		VertexFormat(center + glm::vec3(7 * r, -(4 * r), 0.0f), color), /* 5 */
		VertexFormat(center + glm::vec3(4 * r, -(7 * r), 0.0f), color), /* 6 */
		VertexFormat(center + glm::vec3(0.0f, -(8 * r), 0.0f), color), /* 7 */
		VertexFormat(center + glm::vec3(-(4 * r), -(7 * r), 0.0f), color), /* 8 */
		VertexFormat(center + glm::vec3(-(7 * r), -(4 * r), 0.0f), color), /* 9 */
		VertexFormat(center + glm::vec3(-(8 * r), 0.0f, 0.0f), color), /* 10 */
		VertexFormat(center + glm::vec3(-(7 * r), 4 * r, 0.0f), color), /* 11 */
		VertexFormat(center + glm::vec3(-(4 * r), 7 * r, 0.0f), color), /* 12 */
	};

	Mesh* circle = new Mesh(name);
	std::vector<unsigned short> indices = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

	if (!fill) {
		circle->SetDrawMode(GL_LINE_LOOP);
	}
	else {
		for (size_t i = 0; i < 11; i++)
		{	
			indices.push_back(i + 2);
			indices.push_back(i + 1);
			indices.push_back(0);
			
		}

		indices.push_back(1);
		indices.push_back(12);
		indices.push_back(0);

	}

	circle->InitFromData(vertices, indices);
	return circle;
}
