package core;

import interfaces.Card;
import interfaces.CardConstructor;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/***
 * Class to dynamically load cards from their java files
 */
public class CardFactory 
{
    private static final Map<String, CardConstructor> cardMap = new HashMap<>();
    private static final CardFactory instance = new CardFactory();

    private CardFactory() 
    {
        loadCardsFromDirectory(getClass().getClassLoader().getResource("cards"));
    }

    /***
     * Construct a single card. This method is called by the card in their static initalizers.
     * 
     * @param s The card's name
     * 
     * @return		Card		The constructed card, or a RuntimeException if an error occurred
     */
    public static Card construct(String s) 
    {
        CardConstructor cardConstructor = cardMap.get(s);
        if(cardConstructor == null) 
        {
            throw new RuntimeException("card " + s + " did not register itsef");
        }

        return cardConstructor.create();
    }

    /***
     * Gets the card map size
     * 
     * @return		int			Size of the card map
     */
    public static int size() 
    {
        return cardMap.size();
    }

    /***
     * Register (NOT create) a card into the card map
     * 
     * @param s The card's name
     * @param c The card's constructor
     */
    public static void register(String s, CardConstructor c)
	{
        cardMap.put(s, c);
    }

    /***
     * Get the list of known card names
     * 
     * @return		Set<String>		The list of known card names
     */
    public static Set<String> known_cards() 
    {
        return cardMap.keySet();
    }

    /***
     * Load cards from a directory
     * 
     * @param path the directory's path
     */
    private void loadCardsFromDirectory(URL path)
    {
        Scanner classDirectoryScanner;
		
        try
        {
            classDirectoryScanner = new Scanner(path.openStream());
        }
		catch(IOException ex)
        {
            throw new RuntimeException("Cannot read cards directory");
        }

        while(classDirectoryScanner.hasNext())
        {
            String fileName = classDirectoryScanner.next();

            if(fileName.endsWith(".class") && !fileName.contains("$"))
            {
                loadClass(fileName);
            }
        }
    }

    /***
     * Dynamically load a single class, calling its static initializer
     * 
     * @param fileName The class filename
     */
    private void loadClass(String fileName)
    {
        boolean wasLoaded = true;
        String classname = fileName.substring(0, fileName.length() - ".class".length());
		
        try
        {
            Class.forName("cards." + classname);
        }
        catch(ClassNotFoundException ex)
        {
            wasLoaded = false;
        }
		
        if(wasLoaded)
        {
            System.out.println(fileName + " loaded");
        }
    }
}
