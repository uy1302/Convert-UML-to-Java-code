# Java Code Generator

A tool to convert UML class diagrams from Draw.io into Java code. This guide will walk you through creating your UML class diagram, exporting it, and using this tool for conversion.

## Steps

### 1. Create a UML Class Diagram
   - Open [Draw.io](https://app.diagrams.net/).
   - Design your UML class diagram.
   - Export the diagram:
     - Go to **File > Export as > XML**.
     - Choose the **Compressed** format.

### 2. Prepare the XML File
   - Download the exported XML file.
   - Move the XML file to the `Convert-UML-to-Java-code` folder.
   - Rename the file extension from `.xml` to `.drawio`.

### 3. Convert to Java Code
   - Run the script `gui.py`:
     ```bash
     python gui.py
     ```
   - In the GUI, select your `.drawio` file.
   - Click **Convert** to generate Java code.

## Example

Here’s a quick example of converting a UML class diagram to Java code:

1. Create your diagram in Draw.io and save it as `Example.drawio.xml`.
2. Place `Example.drawio.xml` in the `Convert-UML-to-Java-code` folder and rename it to `Example.drawio`.
3. Run the GUI, select `Example.drawio`, and convert it to see the Java code output.
