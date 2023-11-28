package testing;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.Persistence;
import main.PersistenceFactory;
import main.Profile;
import main.ProfileHandler;
import main.PersistenceFactory.persistenceType;
import main.Returns;

public class ProfileHandlerTest 
{

    @After
    public void cleanUp()
    {
        PersistenceFactory pf = new PersistenceFactory();
        Persistence p = pf.getPersistent(persistenceType.JsonFile);

        p.delete("TestProfile");
    }

    /**
     * Testing the createNewProfile method when valid profile data is entered
     */
    @Test
    public void testCreatingValidProfile()
    {
        Returns returnVal = ProfileHandler.createNewProfile("TestProfile", "password");
        assertEquals(returnVal.getReturnCode(), 0);
    
    }

    /**
     * Testing the createNewProfile method when an invalid userName is entered
     */
    @Test
    public void testInvalidUsernameWhenCreatingProfile()
    {
        Returns returnVal = ProfileHandler.createNewProfile("", "password");
        assertEquals(returnVal.getReturnCode(), -101);
    }

    /**
     * Testing the createNewProfile method when a password that is too short is entered
     */
    @Test
    public void testTooShortPasswordWhenCreatingProfile()
    {
        Returns returnVal = ProfileHandler.createNewProfile("TestProfile", "shortPW");
        assertEquals(returnVal.getReturnCode(), -102);
    }

    /**
     * Testing the createNewProfile method when an empty password is entered
     */
    @Test
    public void testEmptyPasswordWhenCreatingProfile()
    {
        Returns returnVal = ProfileHandler.createNewProfile("TestProfile", "");
        assertEquals(returnVal.getReturnCode(), -102);
    }

    /**
     * Testing the createNewProfile method when an already taken username is entered
     */
    @Test
    public void testTakenUsernameWhenCreatingProfile()
    {
        ProfileHandler.createNewProfile("TestProfile", "password1");

        Returns returnVal = ProfileHandler.createNewProfile("TestProfile", "password2");
        assertEquals(returnVal.getReturnCode(), -106);
    }

    /**
     * Testing the logIn method when the account trying to be logged into exists and the correct usermame and password are provided
     */
    @Test
    public void testLogInExistingProfile()
    {
        ProfileHandler.createNewProfile("TestProfile", "password");

        Returns returnVal = ProfileHandler.login("TestProfile", "password");
        assertEquals(returnVal.getReturnCode(), 0);
    }

    /**
     * Testing the logIn method when the username entered is invalid
     */
    @Test
    public void testLogInInvalidUsername()
    {
        ProfileHandler.createNewProfile("TestProfile", "password");

        Returns returnVal = ProfileHandler.login("", "password");
        assertEquals(returnVal.getReturnCode(), -101);
    }

    /**
     * Testing the logIn method when the password entered is invalid
     */
    @Test
    public void testLogInInvalidPassword()
    {
        ProfileHandler.createNewProfile("TestProfile", "password");

        Returns returnVal = ProfileHandler.login("TestProfile", "");
        assertEquals(returnVal.getReturnCode(), -102);
    }

    /**
     * Testing the logIn method when the profile that is trying to be logged into does not exist
     */
    @Test
    public void testLogInNonexistantProfile()
    {
        Returns returnVal = ProfileHandler.login("TestProfile", "password");
        assertEquals(returnVal.getReturnCode(), -104);
    }

    /**
     * Testing the logIn method when the password entered is not the same for the username
     */
    @Test
    public void testLogInIncorrectPassword()
    {
        ProfileHandler.createNewProfile("TestProfile", "password");

        Returns returnVal = ProfileHandler.login("TestProfile", "passwor");
        assertEquals(returnVal.getReturnCode(), -104);
    }

    /**
     * Testing the logOut method
     */
    @Test
    public void testLogOut()
    {
        ProfileHandler.createNewProfile("TestProfile", "password");
        ProfileHandler.login("TestProfile", "password");

        boolean returnVal = ProfileHandler.logOut();
        assertEquals(returnVal, true);
    }

    /**
     * Testing the editCurrentProfileUsername method when a valid and not taken username is input
     */
    @Test
    public void testEditCurrentProfileValidUsername()
    {
        ProfileHandler.createNewProfile("TestProfile", "password");
        ProfileHandler.login("TestProfile", "password");

        Returns returnVal = ProfileHandler.editCurrentProfileUsername("EditedTestProfile");
        assertEquals(returnVal.getReturnCode(), 0);

        PersistenceFactory pf = new PersistenceFactory();
        Persistence p = pf.getPersistent(persistenceType.JsonFile);
        p.delete("EditedTestProfile");
    }

    /**
     * Testing the editCurrentProfileUsername method when an invalid username is input
     */
    @Test
    public void testEditCurrentProfileInvalidUsername()
    {
        ProfileHandler.createNewProfile("TestProfile", "password");
        ProfileHandler.login("TestProfile", "password");

        Returns returnVal = ProfileHandler.editCurrentProfileUsername("");
        assertEquals(returnVal.getReturnCode(), -101);
    }

    /**
    * Testing the editCurrentProfileUsername method when an already taken username is input
    */
    @Test
    public void testEditCurrentProfileAlreadyTakenUsername()
    {
        ProfileHandler.createNewProfile("TestProfile", "password1");
        ProfileHandler.createNewProfile("TakenUsername", "password2");
        ProfileHandler.login("TestProfile", "password1");

        Returns returnVal = ProfileHandler.editCurrentProfileUsername("TakenUsername");
        assertEquals(returnVal.getReturnCode(), -106);

        PersistenceFactory pf = new PersistenceFactory();
        Persistence p = pf.getPersistent(persistenceType.JsonFile);
        p.delete("TakenUsername");
    }

     /**
     * Testing the editCurrentProfilePassword method when a valid password is input
     */
    @Test
    public void testEditCurrentProfileValidPassword()
    {
        ProfileHandler.createNewProfile("TestProfile", "password");
        ProfileHandler.login("TestProfile", "password");

        Returns returnVal = ProfileHandler.editCurrentProfilePassword("newPW1234567");
        assertEquals(returnVal.getReturnCode(), 0);
    }

    /**
     * Testing the editCurrentProfilePassword method when a too short password is input
     */
    @Test
    public void testEditCurrentProfileTooShortPassword()
    {
        ProfileHandler.createNewProfile("TestProfile", "password");
        ProfileHandler.login("TestProfile", "password");

        Returns returnVal = ProfileHandler.editCurrentProfilePassword("shortPW");
        assertEquals(returnVal.getReturnCode(), -102);
    }

    /**
     * Testing the editCurrentProfilePassword method when an empty password is input
     */
    @Test
    public void testEditCurrentProfileEmptyPassword()
    {
        ProfileHandler.createNewProfile("TestProfile", "password");
        ProfileHandler.login("TestProfile", "password");

        Returns returnVal = ProfileHandler.editCurrentProfilePassword("");
        assertEquals(returnVal.getReturnCode(), -102);
    }

    /**
     * Testing the deleteCurrentProfile method
     */
    @Test
    public void testDeleteCurrentProfile()
    {
        ProfileHandler.createNewProfile("TestProfile", "password");
        ProfileHandler.login("TestProfile", "password");

        boolean returnVal = ProfileHandler.deleteCurrentProfile();
        assertEquals(returnVal, true);
    }

}
