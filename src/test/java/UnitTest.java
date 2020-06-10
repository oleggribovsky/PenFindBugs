import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.*;
import java.util.Scanner;

public class UnitTest {
private String testWord = "hrybouski";

    @DataProvider(name = "data-provider")
    public Object[][] dataProviderMethod() {
        return new Object[][]{
                {new Pen(-5).write(testWord), ""},
                {new Pen(0).write(testWord), ""},
                {new Pen(8).write(testWord), "hrybousk"},
                {new Pen(9).write(testWord), "hrybouski"}
        };
    }
    @Test (dataProvider = "data-provider")
    public void validConstructor(String objectGenerated, String str){
        Assert.assertEquals(objectGenerated, str);
    }

    @DataProvider(name = "data-provider-size-letter")
    public Object[][] dataProviderMethodSizeLetter() {
        return new Object[][]{
                {new Pen(9, -10).write(testWord), ""},
                {new Pen(9, 1).write(testWord), "hrybouski"},
                {new Pen(9, 2).write(testWord), "hryb"},
                {new Pen(9, 30000).write(testWord), ""}
        };
    }

    @Test (dataProvider = "data-provider-size-letter")
    public void validConstructorSizeLetter(String stringGenerated, String str){
        Assert.assertEquals(stringGenerated, str);
    }

    @DataProvider(name = "data-provider-size-letter-color")
    public Object[][] dataProviderMethodSizeLetterColor() {
        return new Object[][]{
                {new Pen(5, -10, "GREEN").getColor(), "GREEN"},
        };
    }

    @Test (dataProvider = "data-provider-size-letter-color")
    public void validConstructorSizeLetterColor(String color, String expectedColor){
        Assert.assertEquals(color, expectedColor);
    }

    @Test
    public void readFromConsole() throws FileNotFoundException {
        try {
            File myObj = new File("myfile.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        PrintStream fileStream = new PrintStream("myfile.txt");
        System.setOut(fileStream);
        new Pen(100).doSomethingElse();
        File myObj = new File("myfile.txt");
        Scanner myReader = new Scanner(myObj);
        String data = myReader.nextLine();
        Assert.assertEquals(data, "BLUE");

    }

    @Test
    public void verifyIsWork(){
        Assert.assertTrue(!new Pen(-5).isWork());
        Assert.assertTrue(!new Pen(0).isWork());
        Assert.assertTrue(new Pen(1).isWork());
    }
}
