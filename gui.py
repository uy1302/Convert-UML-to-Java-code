import sys
from PyQt5.QtWidgets import QApplication, QWidget, QVBoxLayout, QLabel, QPushButton, QFileDialog, QMessageBox
from PyQt5.QtCore import Qt
import json
from decode.convert_to_readable import DecodeAndDecompress
from parsers.style_parser import StyleParser
from parsers.syntax_parser import SyntaxParser
from java_generator import JavaCodeGenerator

def json_to_file(file_name, data):
    with open(file_name, "w") as f:
        f.write(json.dumps(data, indent=4))

def remove_xml_declaration(file_path):
    # Read the file content
    with open(file_path, "r", encoding="utf-8") as file:
        lines = file.readlines()

    # Remove the XML declaration line if it exists
    if lines[0].strip().startswith("<?xml"):
        lines = lines[1:]

    # Write back to the file without the XML declaration line
    with open(file_path, "w", encoding="utf-8") as file:
        file.writelines(lines)

class DiagramConverterGUI(QWidget):
    def __init__(self):
        super().__init__()
        self.initUI()

    def initUI(self):
        layout = QVBoxLayout()

        self.label = QLabel("Convert .drawio file to Java Code and JSON", self)
        self.label.setAlignment(Qt.AlignCenter)
        layout.addWidget(self.label)

        self.convertButton = QPushButton("Select .drawio File and Convert", self)
        self.convertButton.clicked.connect(self.select_and_convert_file)
        layout.addWidget(self.convertButton)

        self.removeDeclarationButton = QPushButton("Remove XML Declaration", self)
        self.removeDeclarationButton.clicked.connect(self.remove_declaration)
        layout.addWidget(self.removeDeclarationButton)

        self.setLayout(layout)
        self.setWindowTitle("Diagram Converter")
        self.setGeometry(300, 300, 400, 200)

    def select_and_convert_file(self):
        file_path, _ = QFileDialog.getOpenFileName(self, "Select .drawio file", "", "Draw.io Files (*.drawio)")
        if file_path:
            try:
            
                decoded_xml = DecodeAndDecompress.convert(file_path)
  
                xml_file_path = file_path.replace(".drawio", "_decoded.xml")
                DecodeAndDecompress.write_xml_file(xml_file_path, decoded_xml)
            
                parser_style = StyleParser(decoded_xml)
                style_tree = parser_style.convert_to_style_tree()
                json_to_file(file_path.replace(".drawio", "_style_tree.json"), style_tree)
                
                parser_syntax = SyntaxParser(style_tree)
                syntax_tree = parser_syntax.convert_to_sytax_tree()
                json_to_file(file_path.replace(".drawio", "_syntax_tree.json"), syntax_tree)
                
            
                java_code_gen = JavaCodeGenerator(syntax_tree, "examples/example_code")
                java_code_gen.generate_code()
                QMessageBox.information(self, "Success", "File converted and JSON and Java code generated successfully!")

            except Exception as e:
                QMessageBox.critical(self, "Error", f"Failed to convert file: {e}")

    def remove_declaration(self):
        file_path, _ = QFileDialog.getOpenFileName(self, "Select XML file", "", "XML Files (*.xml)")
        if file_path:
            try:
           
                remove_xml_declaration(file_path)
                QMessageBox.information(self, "Success", "XML declaration removed successfully!")

            except Exception as e:
                QMessageBox.critical(self, "Error", f"Failed to remove XML declaration: {e}")

if __name__ == '__main__':
    app = QApplication(sys.argv)
    ex = DiagramConverterGUI()
    ex.show()
    sys.exit(app.exec_())
