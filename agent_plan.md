# Java 8 to Java 21 Migration Agent Plan

This document outlines the recommended model, system prompt, and example user prompts for building a pipeline to migrate Java 8 applications to Java 21 using a coding agent.

## Recommended Model

For complex tasks like framework and language migrations, use a powerful, instruction-following model with a large context window and strong reasoning capabilities.

**Model:** `gemini-1.5-pro-latest`

**Reasoning:**

*   **Large Context Window:** The migration will involve analyzing multiple files (build scripts, source code). A large context window allows the model to hold more of your codebase in its "memory" at once, leading to more consistent and context-aware changes.
*   **Strong Instruction Following:** You need a model that can rigorously follow a multi-step plan, adhere to conventions, and use specific tools or commands as instructed.
*   **Advanced Reasoning:** Migrations are not simple search-and-replace tasks. They involve understanding dependency graphs, API breaking changes (like the `javax` to `jakarta` transition), and fixing subtle compilation errors. This requires a model with strong logical reasoning.

---

## System Prompt

The system prompt sets the "personality" and rules for the agent. It should be detailed and enforce a methodical, safe workflow.

```markdown
You are an expert software engineer specializing in Java, Spring Boot, and Gradle/Maven migrations. Your primary goal is to safely and efficiently migrate a Java 8 application to Java 21 with the corresponding Spring Boot 3.x upgrade. You must adhere strictly to the following instructions.

# Core Mandates

- **Analyze First:** Before making any changes, always analyze the relevant files (build scripts, source code) to create a clear, step-by-step plan.
- **One Step at a Time:** Execute one logical change at a time. For example, update the Java version in the build script, then upgrade dependencies, then refactor source code. Do not try to do everything at once.
- **Verify, Verify, Verify:** After every single change, you MUST run a build and/or execute the test suite to verify that your change was successful and did not break anything.
- **Project Conventions:** Rigorously adhere to the project's existing conventions. Match the code style, formatting, and architectural patterns.
- **Dependency Management:**
    - First, update the build tool itself (e.g., Gradle Wrapper for Gradle projects, or Maven version for Maven projects) to a version compatible with Java 21.
    - Upgrade Spring Boot to a compatible version (e.g., 3.2.3 or newer).
    - Update other dependencies to versions compatible with your new Spring Boot version. Check public repositories like MVNRepository for compatibility information.
    - NEVER assume a library is available. ALWAYS check `build.gradle` or `pom.xml` before using it.
- **Handle `javax` to `jakarta`:** A critical part of the Spring Boot 3 migration is the package namespace change from `javax.*` to `jakarta.*`. You must meticulously update all imports and code references. Pay special attention to `javax.persistence`, `javax.servlet`, and `javax.validation`.
- **Fix Errors Methodically:** If a build or test fails, analyze the error output carefully. Identify the root cause and apply a targeted fix. Do not make speculative changes. Explain the error and your fix.
```

---

## User Prompts (Example Workflow for `order-service`)

These prompts guide the model through the migration process step-by-step. This sequence is crucial for breaking down the complex task into manageable parts.

#### **Step 1: Planning and Initial Analysis**

**User Prompt:**
> Analyze the `order-service` directory. Your goal is to migrate it from its current Java version to Java 21. This will require updating the Gradle build, upgrading to Spring Boot 3, and refactoring the code. Create a high-level plan for this migration.

#### **Step 2: Update Gradle and Java Version**

**User Prompt:**
> Good. Let's start with the first step.
> 1. Check the current Gradle version and update the Gradle wrapper (`gradle/wrapper/gradle-wrapper.properties`) to a version compatible with Java 21 (e.g., 8.5 or newer).
> 2. Modify the `build.gradle` file to set the Java toolchain version to 21.
> 3. After these changes, run `./gradlew --version` to confirm the new Gradle version and Java version are being used.

#### **Step 3: Upgrade Spring Boot and Dependencies**

**User Prompt:**
> Now that the build system is configured for Java 21, let's update the dependencies.
> 1. In `build.gradle`, upgrade the Spring Boot plugin and dependencies to a modern, non-snapshot version compatible with Java 21 (e.g., `3.2.3`).
> 2. Look for other dependencies, especially those related to Spring Cloud and security (`jjwt`), and update them to compatible versions. You may need to search public Maven repositories for the correct versions.
> 3. After updating the file, run `./gradlew build` to see the new dependency-related compilation errors. We will fix these in the next step.

#### **Step 4: Refactor Code (`javax` to `jakarta`)**

**User Prompt:**
> The build is failing because of the `javax` to `jakarta` namespace change.
> 1. Find all Java files that use `javax.*` imports.
> 2. Replace all `javax.servlet.*` imports with `jakarta.servlet.*`.
> 3. Replace all `javax.persistence.*` imports with `jakarta.persistence.*`.
> 4. Pay special attention to security configurations (`WebSecurityConfig.java`) and JWT filters, as `WebSecurityConfigurerAdapter` is deprecated and needs to be replaced with a `SecurityFilterChain` bean.
> 5. Run `./gradlew build` again. If it fails, analyze the error and apply fixes until the code compiles.

#### **Step 5: Run Tests and Fix Failures**

**User Prompt:**
> The code is compiling, but the tests are failing. Run the test suite using `./gradlew test` and analyze the output. It looks like there are issues with Testcontainers and JWT key generation.
> 1.  Address the test failures one by one.
> 2.  Ensure the MongoDB Testcontainer is configured correctly.
> 3.  Fix any exceptions related to weak JWT keys or signature mismatches. This might require generating a stronger key or ensuring the same key is used for signing and verification in tests.
> 4. After each fix, re-run the tests until they all pass.

---

## Single User Prompt for Full Migration

**Warning:** This method is significantly more error-prone. A single mistake by the model in any step can lead to a completely non-functional result without any easy way to debug the intermediate stages. Use this with caution.
**Note on Validation:** This prompt instructs the model to perform analysis and migration (code changes). However, a single prompt to a text-output-only model cannot perform real-time validation (like building, running tests, or iteratively fixing errors). Validation steps must be external to the model's output and handled by your pipeline.

**User Prompt:**
> **Overall Goal:**
> You are an expert Java migration agent. Your task is to perform a complete migration of the service located in the `[service-directory-name]` directory from Java 8/Spring Boot 2 to Java 21/Spring Boot 3.
>
> **Required Steps & Output Format:**
> You must analyze the project and provide a response containing the complete, updated content for ALL files that need to be changed to complete this migration. Do not describe your changes in prose; your output must only be a sequence of file content blocks.
>
> For each file, use the following format:
>
> ```
> ---
> File: [Full path to the file, e.g., user-service/pom.xml]
> ---
> [Complete, updated content of the file]
> ```
>
> **Migration Checklist:**
> Your generated file contents must accomplish all of the following tasks:
>
> 1.  **Update Build Configuration:**
>     *   Update the build tool (e.g., Gradle wrapper or Maven plugins) to a version compatible with Java 21.
>     *   Modify the `build.gradle` or `pom.xml` to:
>         *   Set the Java version to `21`.
>         *   Upgrade the Spring Boot parent/plugin to a modern, compatible version (e.g., `3.2.3`).
>         *   Replace `io.springfox` (Swagger) dependencies with the `org.springdoc:springdoc-openapi-starter-webmvc-ui` dependency.
>         *   Update other dependencies like `jjwt`, testing libraries, etc., to versions compatible with Spring Boot 3.
>
> 2.  **Refactor Java Source Code:**
>     *   For every `.java` file in the source tree (`src/main/java`, `src/test/java`):
>         *   Replace all `javax.*` package imports (e.g., `javax.servlet`, `javax.persistence`, `javax.validation`) with their corresponding `jakarta.*` equivalents.
>         *   Refactor code to address breaking changes from the Spring Boot 3 upgrade. This is especially important for Spring Security configurations, where `WebSecurityConfigurerAdapter` must be replaced with a `SecurityFilterChain` bean.

---

## Ultra-Simple, Single-File Transformation Prompts

These templates are designed for models that struggle with multi-step or conversational instructions. They focus on transforming one file at a time with a very direct instruction to output *only* the new file content.

**Important Note:** Template 1 and Template 2 are for transforming *separate files*. Do not combine them into a single prompt, as this will reduce reliability and is essentially re-creating the more complex "Single User Prompt for Full Migration". Use one template per file needing transformation.

#### **Template 1: For `pom.xml` or `build.gradle`**

**User Prompt:**
> **Task:** Your only task is to take the following `[pom.xml` or `build.gradle`]` file, which is for a Java 8 / Spring Boot 2 project, and update it to be compatible with Java 21 / Spring Boot 3.2.3. This includes upgrading the parent, setting the Java version, replacing `springfox` with `springdoc`, and updating all other dependencies to compatible versions.
>
> Your output must ONLY be the complete, new `[pom.xml` or `build.gradle`]` content. Do not add any explanation, preamble, or formatting.
>
> **Input `[pom.xml` or `build.gradle`]`:**
> ```xml
> [Paste the ENTIRE content of the original pom.xml or build.gradle here]
> ```

#### **Template 2: For a `.java` file**

**User Prompt:**
> **Task:** Your only task is to take the following Java file, which uses the `javax.*` namespace and old Spring Boot APIs, and update it to be compatible with Spring Boot 3. This means you must replace all `javax.*` imports with their `jakarta.*` equivalents and refactor any deprecated code (like `WebSecurityConfigurerAdapter`).
>
> Your output must ONLY be the complete, new content of the Java file. Do not add any explanation, preamble, or formatting.
>
> **Input Java File (`[path/to/file.java]`)**
> ```java
> [Paste the ENTIRE content of the original Java file here]
> ```