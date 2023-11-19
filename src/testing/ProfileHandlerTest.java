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
        int returnCode = ProfileHandler.createNewProfile("TestProfile", "password");
        assertEquals(returnCode, 0);
    
    }

    /**
     * Testing the createNewProfile method when an invalid userName is entered
     */
    @Test
    public void testInvalidUsernameWhenCreatingProfile()
    {
        int returnCode = ProfileHandler.createNewProfile("", "password");
        assertEquals(returnCode, -101);
    }

    /**
     * Testing the createNewProfile method when a password that is too short is entered
     */
    @Test
    public void testTooShortPasswordWhenCreatingProfile()
    {
        int returnCode = ProfileHandler.createNewProfile("TestProfile", "shortPW");
        assertEquals(returnCode, -102);
    }

    /**
     * Testing the createNewProfile method when an empty password is entered
     */
    @Test
    public void testEmptyPasswordWhenCreatingProfile()
    {
        int returnCode = ProfileHandler.createNewProfile("TestProfile", "");
        assertEquals(returnCode, -102);
    }

    /**
     * Testing the createNewProfile method when an already taken username is entered
     */
    @Test
    public void testTakenUsernameWhenCreatingProfile()
    {
        ProfileHandler.createNewProfile("TestProfile", "password1");

        int returnCode = ProfileHandler.createNewProfile("TestProfile", "password2");
        assertEquals(returnCode, -106);
    }

    /**
     * Testing the logIn method when the account trying to be logged into exists and the correct usermame and password are provided
     */
    @Test
    public void testLogInExistingProfile()
    {
        ProfileHandler.createNewProfile("TestProfile", "password");

        int returnCode = ProfileHandler.login("TestProfile", "password");
        assertEquals(returnCode, 0);
    }

    /**
     * Testing the logIn method when the username entered is invalid
     */
    @Test
    public void testLogInInvalidUsername()
    {
        ProfileHandler.createNewProfile("TestProfile", "password");

        int returnCode = ProfileHandler.login("", "password");
        assertEquals(returnCode, -101);
    }

    /**
     * Testing the logIn method when the password entered is invalid
     */
    @Test
    public void testLogInInvalidPassword()
    {
        ProfileHandler.createNewProfile("TestProfile", "password");

        int returnCode = ProfileHandler.login("TestProfile", "");
        assertEquals(returnCode, -102);
    }

    /**
     * Testing the logIn method when the profile that is trying to be logged into does not exist
     */
    @Test
    public void testLogInNonexistantProfile()
    {
        int returnCode = ProfileHandler.login("TestProfile", "password");
        assertEquals(returnCode, -104);
    }

    /**
     * Testing the logIn method when the password entered is not the same for the username
     */
    @Test
    public void testLogInIncorrectPassword()
    {
        ProfileHandler.createNewProfile("TestProfile", "password");

        int returnCode = ProfileHandler.login("TestProfile", "passwor");
        assertEquals(returnCode, -104);
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

        int returnCode = ProfileHandler.editCurrentProfileUsername("EditedTestProfile");
        assertEquals(returnCode, 0);

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

        int returnCode = ProfileHandler.editCurrentProfileUsername("");
        assertEquals(returnCode, -101);
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

        int returnCode = ProfileHandler.editCurrentProfileUsername("TakenUsername");
        assertEquals(returnCode, -106);

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

        int returnCode = ProfileHandler.editCurrentProfilePassword("newPW1234567");
        assertEquals(returnCode, 0);
    }

    /**
     * Testing the editCurrentProfilePassword method when a too short password is input
     */
    @Test
    public void testEditCurrentProfileTooShortPassword()
    {
        ProfileHandler.createNewProfile("TestProfile", "password");
        ProfileHandler.login("TestProfile", "password");

        int returnCode = ProfileHandler.editCurrentProfilePassword("shortPW");
        assertEquals(returnCode, -102);
    }

    /**
     * Testing the editCurrentProfilePassword method when an empty password is input
     */
    @Test
    public void testEditCurrentProfileEmptyPassword()
    {
        ProfileHandler.createNewProfile("TestProfile", "password");
        ProfileHandler.login("TestProfile", "password");

        int returnCode = ProfileHandler.editCurrentProfilePassword("");
        assertEquals(returnCode, -102);
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
