package core;

import interfaces.CardConstructor;

/***
 * Static initializer to register cards as soon as their classes are loaded by Java
 */
public class StaticInitializer
{
    public StaticInitializer(String string, CardConstructor cardConstructor)
    {
        CardFactory.register(string, cardConstructor);
    }
}
