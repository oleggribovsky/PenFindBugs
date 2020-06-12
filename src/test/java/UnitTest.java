import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.*;
import java.util.Scanner;

public class UnitTest {
private String testWord = "hrybouski";


    // test constructor with 1 parameter: valid inkContainerValue
    @DataProvider(name = "data-provider")
    public Object[][] dataProviderMethod() {
        return new Object[][]{
                {0, ""},//zero value
                {8, "hrybousk"},//less than size of testWord
                {9, "hrybouski"},//equal to the size of testWord
                {2147483647, "hrybouski"} //max integer value

        };
    }
    @Test(dataProvider = "data-provider")
    public void validConstructor(int value, String word){
            Assert.assertEquals(new Pen(value).write(testWord), word);

    }


    // test constructor with 1 parameter: negative inkContainerValue
    @DataProvider(name = "data-provider-exception")
    public Object[][] dataProviderMethodException() {
        return new Object[][]{
                {-5}//negative size of inkContainerValue

        };
    }
    @Test (dataProvider = "data-provider-exception", expectedExceptions = ArithmeticException.class)
    public void validConstructorNegativeValue(int value){
        new Pen(value);
        //BUG 1 Constructor should throw an exception
    }



    // test constructor with 2 parameters: valid inkContainerValue, valid sizeLetter
    @DataProvider(name = "data-provider-size-letter")
    public Object[][] dataProviderMethodSizeLetter() {
        return new Object[][]{
                {9, 1.0, "hrybouski"},//with this sizeLetter whole word is written
    //BUG 2 With sizeLetter 2 and inkContainterValue 9 half of word should be written instead of "hrybouski"
                {9, 2.0, "hryb"},//with this sizeLetter half a word is written
    //BUG 3 The size of word does not depends on the sizeLetter value. Output is "hrybouski" instead of ""
                {9, 30000.8, ""}//with this size letter almost nothing should be written
        };
    }
    @Test (dataProvider = "data-provider-size-letter")
    public void validConstructorSizeLetter(int inkContainer, double sizeLetter, String expectedString){
        Assert.assertEquals(new Pen(inkContainer, sizeLetter), expectedString);
    }



    //test constructor with 2 parameters: invalid (negative) value of sizeLettter, constructor should throw an exception
    @DataProvider(name = "data-provider-size-letter-exception")
    public Object[][] dataProviderSizeLetterException() {
        return new Object[][]{
                {-10}
        };
    }
    @Test (dataProvider = "data-provider-size-letter-exception", expectedExceptions = ArithmeticException.class)
    public void validConstructor1(double letterSize){
        new Pen(100, letterSize);
        //BUG 4 Constructor should throw an exception
    }


    //test constructor with 3 parameters
    @DataProvider(name = "data-provider-size-letter-color")
    public Object[][] dataProviderMethodSizeLetterColor() {
        return new Object[][]{

                {5, 5, "GREEN", "GREEN"},
        };
    }
    @Test (dataProvider = "data-provider-size-letter-color")
    public void validConstructorSizeLetterColor(int inkContainerValue, double sizeLetter, String color, String expectedColor){
        Assert.assertEquals(new Pen(inkContainerValue, sizeLetter, color).getColor(), expectedColor);
        //BUG 5 Output is BLUE instead of GREEN
    }


    // test of doSomethingElse method (prints to console)
    @Test
    public void readFromConsole() throws FileNotFoundException {
        try {
            File myObj = new File("myfile.txt");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                //System.out.println("File already exists.");
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

    //test of isWork method
    @Test
    public void verifyIsWork(){
        Assert.assertTrue(!new Pen(-5).isWork());
        Assert.assertTrue(!new Pen(0).isWork());
        Assert.assertTrue(new Pen(1).isWork());
    }
}
