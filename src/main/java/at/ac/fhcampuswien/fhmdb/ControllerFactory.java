package at.ac.fhcampuswien.fhmdb;

import javafx.util.Callback;

public class ControllerFactory implements Callback<Class<?>, Object>
{
    // TODO: create MyCtrl as singleton instance
    private static HomeController instance;
    @Override
    public Object call(Class<?> aClass)
    {
        // TODO: check if MyCtrl is already instantiated
        if (aClass == HomeController.class)
        {
            if (instance == null)
                instance = new HomeController();
            return instance;
        }

        try
        {
            return aClass.getDeclaredConstructor().newInstance();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

}
