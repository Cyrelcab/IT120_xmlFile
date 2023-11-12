import java.io.File;
import java.util.Scanner; 
// import java.util.ArrayList;
// import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class GradeXMLFile {

    private Document doc;
    private String xmlFilePath;

    public GradeXMLFile(String xmlFilePath) {
        this.xmlFilePath = xmlFilePath;
        initializeXmlDocument();
    }

    private void initializeXmlDocument() {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            File xmlFile = new File(xmlFilePath);

            if (xmlFile.exists()) {
                doc = dBuilder.parse(xmlFile);
            } else {
                doc = dBuilder.newDocument();
                Element rootElement = doc.createElement("Grades");
                doc.appendChild(rootElement);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addGradeEntry(String course, String grade) {
        Element gradeElement = doc.createElement("grade");
        Element courseElement = doc.createElement("course");
        courseElement.appendChild(doc.createTextNode(course));
        Element gradeValueElement = doc.createElement("grades");
        gradeValueElement.appendChild(doc.createTextNode(grade));

        gradeElement.appendChild(courseElement);
        gradeElement.appendChild(gradeValueElement);

        doc.getDocumentElement().appendChild(gradeElement);
        saveChanges();
        System.out.println("You've successfully add the data");
    }

    public void editGradeEntry(String course, String newGrade) {
        Element grades = doc.getDocumentElement();
        Element grade = findGradeElementByCourse(course);

        if (grade != null) {
            Element gradeValue = (Element) grade.getElementsByTagName("value").item(0);
            gradeValue.setTextContent(newGrade);
            saveChanges();
        }
    }

    public void removeGradeEntry(String course) {
        Element grades = doc.getDocumentElement();
        Element grade = findGradeElementByCourse(course);

        if (grade != null) {
            grades.removeChild(grade);
            saveChanges();
        }
    }

    public void saveChanges() {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(xmlFilePath));
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Element findGradeElementByCourse(String course) {
        Element grades = doc.getDocumentElement();
        for (int i = 0; i < grades.getElementsByTagName("grade").getLength(); i++) {
            Element grade = (Element) grades.getElementsByTagName("grade").item(i);
            Element courseElement = (Element) grade.getElementsByTagName("course").item(0);
            if (courseElement.getTextContent().equals(course)) {
                return grade;
            }
        }
        return null;
    }
    
    public void clearConsole() {
        try {
            final String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                // For Windows
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // For Unix/Linux/Mac
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final Exception e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String xmlFilePath = "C:\\Users\\cabod\\OneDrive\\Desktop\\120\\grades.xml";
        GradeXMLFile gradeXML = new GradeXMLFile(xmlFilePath);
        String inputCourse;
        String inputGrade;
        int choice;
        
        do {
            System.out.println("IT 120 Activity");
            System.out.println("[1] Add course and grade to XML File");
            System.out.println("[2] Edit Grade in XML File");
            System.out.println("[3] Delete data from XML");
            System.out.println("[0] Exit");
            System.out.print("Enter your choice: ");
            choice = input.nextInt();
            input.nextLine(); // Consume the newline character
        
            switch (choice) {
                case 1:
                    String addMore = "y";
                    do {
                        gradeXML.clearConsole();
                        System.out.println("Add data to XML File here:");
                        System.out.print("Course: ");
                        inputCourse = input.nextLine();
        
                        System.out.print("Grade: ");
                        inputGrade = input.nextLine();
        
                        gradeXML.addGradeEntry(inputCourse, inputGrade);
        
                        System.out.println("Do you want to add more data? [y/n]");
                        addMore = input.nextLine();
                    } while (addMore.equals("y"));

                    gradeXML.clearConsole();
                    break;
        
                case 2:
                    // Add code for editing data here
                    // Uncomment the case, and implement the edit functionality
                    String editMore = "y";
                    do{
                        gradeXML.clearConsole();
                        System.out.println("Edit your grade here:");
                        System.out.print("Enter the course you want to edit: ");
                        inputCourse = input.nextLine();

                        System.out.print("Enter the edited grade: ");
                        inputGrade = input.nextLine();

                        gradeXML.editGradeEntry(inputCourse, inputGrade);

                        System.out.print("Do you want to edit your grade in other course? [y/n]");
                        editMore = input.nextLine();

                    }while(editMore.equals("y"));
        
                    gradeXML.clearConsole();
                    break;
        
                case 3:
                    // Add code for deleting data here
                    // Uncomment the case, and implement the delete functionality
        
                    break;
        
                case 0:
                    System.out.println("Exiting program. Goodbye!");
                    break;
        
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
        
        input.close(); // Close the scanner outside the loop
        

    }
}
