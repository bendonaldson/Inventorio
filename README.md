# Inventorio

## Project Overview

This is a backend application for a simple inventory management system, built using the Spring Boot framework. The project is designed to showcase modern backend development skills, including building RESTful APIs, managing data persistence with a relational database, and implementing a professional development workflow.

The API provides core functionalities for managing products, categories, orders, and user authentication, simulating a real-world application backend.

## Features
* Product Management: Full CRUD (Create, Read, Update, Delete) for products with details like name, description, price, and stock quantity.
* Category Management: Organize products into categories with a many-to-one relationship.
* Order Processing: A transactional system for creating orders, which automatically validates stock levels and deducts quantities
* User Authentication: A secure login and registration system using JSON Web Tokens (JWT).
* Role-Based Access Control: Differentiates between regular users (who can place orders) and administrators (who can manage products).
* Search and Filtering: API endpoints for searching products by name and filtering by category or price.