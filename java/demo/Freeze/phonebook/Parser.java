// **********************************************************************
//
// Copyright (c) 2001
// Mutable Realms, Inc.
// Huntsville, AL, USA
//
// All Rights Reserved
//
// **********************************************************************

class Parser
{
    Parser(Ice.Communicator communicator, PhoneBookPrx phoneBook)
    {
	_communicator = communicator;
	_phoneBook = phoneBook;
    }

    void
    usage()
    {
	System.err.print(
	    "help             Print this message.\n" +
	    "exit, quit       Exit this program.\n" +
	    "add NAMES...     Create new contacts for NAMES in the phonebook.\n" +
	    "find NAME        Find all contacts in the phonebook that match NAME.\n" +
	    "                 Set the current contact to the first one found.\n" +
	    "next             Set the current contact to the next one that was found.\n" +
	    "current          Display the current contact.\n" +
	    "name NAME        Set the name for the current contact to NAME.\n" +
	    "address ADDRESS  Set the address for the current contact to ADDRESS.\n" +
	    "phone PHONE      Set the phone number for the current contact to PHONE.\n" +
	    "remove           Permanently remove the current contact from the phonebook.\n" +
	    "size SIZE        Set the evictor size for contacts to SIZE.\n" +
	    "shutdown         Shut the phonebook server down.\n");
    }

    void
    addContacts(java.util.List args)
    {
	if(args.isEmpty())
	{
	    error("`add' requires at least one argument (type `help' for more info)");
	    return;
	}
	
	try
	{
	    java.util.Iterator p = args.iterator();
	    while(p.hasNext())
	    {
		ContactPrx contact = _phoneBook.createContact();
		String name = (String)p.next();
		contact.setName(name);
		System.out.println("added new contact for `" + name + "'");
	    }
	}
	catch(DatabaseException ex)
	{
	    error(ex.message);
	}
	catch(Ice.LocalException ex)
	{
	    error(ex.toString());
	}
    }

    void
    findContacts(java.util.List args)
    {
	if(args.size() != 1)
	{
	    error("`find' requires exactly one argument (type `help' for more info)");
	    return;
	}
	
	try
	{
	    String name = (String)args.get(0);
	    _foundContacts = _phoneBook.findContacts(name);
	    _current = 0;
	    System.out.println("number of contacts found: " + _foundContacts.length);
	    printCurrent();
	}
	catch(DatabaseException ex)
	{
	    error(ex.message);
	}
	catch(Ice.LocalException ex)
	{
	    error(ex.toString());
	}
    }

    void
    nextFoundContact()
    {
	if(_current != _foundContacts.length)
	{
	    ++_current;
	}
	printCurrent();
    }
    
    void
    printCurrent()
    {
	try
	{
	    if(_current != _foundContacts.length)
	    {
		System.out.println("current contact is:" );
		System.out.println("name: " + _foundContacts[_current].getName());
		System.out.println("address: " + _foundContacts[_current].getAddress() );
		System.out.println("phone: " + _foundContacts[_current].getPhone());
	    }
	    else
	    {
		System.out.println("no current contact");
	    }
	}
	catch(Ice.LocalException ex)
	{
	    error(ex.toString());
	}
    }

    void
    setCurrentName(java.util.List args)
    {
	if(args.size() != 1)
	{
	    error("`name' requires exactly one argument (type `help' for more info)");
	    return;
	}

	try
	{
	    if(_current != _foundContacts.length)
	    {
		String name = (String)args.get(0);
		_foundContacts[_current].setName(name);
		System.out.println("changed name to `" + name + "'");
	    }
	    else
	    {
		System.out.println("no current contact");
	    }
	}
	catch(DatabaseException ex)
	{
	    error(ex.message);
	}
	catch(Ice.LocalException ex)
	{
	    error(ex.toString());
	}
    }

    void
    setCurrentAddress(java.util.List args)
    {
	if(args.size() != 1)
	{
	    error("`address' requires exactly one argument (type `help' for more info)");
	    return;
	}

	try
	{
	    if(_current != _foundContacts.length)
	    {
		String addr = (String)args.get(0);
		_foundContacts[_current].setAddress(addr);
		System.out.println( "changed address to `" + addr + "'" );
	    }
	    else
	    {
		System.out.println( "no current contact" );
	    }
	}
	catch(Ice.LocalException ex)
	{
	    error(ex.toString());
	}
    }

    void
    setCurrentPhone(java.util.List args)
    {
	if(args.size() != 1)
	{
	    error("`phone' requires exactly one argument (type `help' for more info)");
	    return;
	}

	try
	{
	    
	    if(_current != _foundContacts.length)
	    {
		String number = (String)args.get(0);
		_foundContacts[_current].setPhone(number);
		System.out.println( "changed phone number to `" + number + "'" );
	    }
	    else
	    {
		System.out.println( "no current contact" );
	    }
	}
	catch(Ice.LocalException ex)
	{
	    error(ex.toString());
	}
    }

    void
    removeCurrent()
    {
	try
	{
	    if(_current != _foundContacts.length)
	    {
		_foundContacts[_current].destroy();
		System.out.println( "removed current contact" );
	    }
	    else
	    {
		System.out.println( "no current contact" );
	    }
	}
	catch(DatabaseException ex)
	{
	    error(ex.message);
	}
	catch(Ice.LocalException ex)
	{
	    error(ex.toString());
	}
    }

    void
    setEvictorSize(java.util.List args)
    {
	if(args.size() != 1)
	{
	    error("`size' requires exactly one argument (type `help' for more info)");
	    return;
	}

	String s = (String)args.get(0);
	try
	{
	    _phoneBook.setEvictorSize(Integer.parseInt(s));
	}
	catch(NumberFormatException ex)
	{
	    error("not a number " + s);
	}
	catch(DatabaseException ex)
	{
	    error(ex.message);
	}
	catch(Ice.LocalException ex)
	{
	    error(ex.toString());
	}
    }

    void
    shutdown()
    {
	try
	{
	    _phoneBook.shutdown();
	}
	catch(Ice.LocalException ex)
	{
	    error(ex.toString());
	}
    }

    void
    error(String s)
    {
	System.err.println("error: " + s);
    }

    void
    warning(String s)
    {
	System.err.println("warning: " + s);
    }

    String
    getInput()
    {
	if(_interactive)
	{
	    System.out.print(">>> ");
	    System.out.flush();
	}

	try
	{
	    return _in.readLine();
	}
	catch(java.io.IOException e)
	{
	    return null;
	}
    }

    int
    parse()
    {
	_foundContacts = new ContactPrx[0];
	_current = 0;

	_in = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
	_interactive = true;

	Grammar g = new Grammar(this);
	g.parse();

	return 0;
    }

    int
    parse(java.io.BufferedReader in)
    {
	_foundContacts = new ContactPrx[0];
	_current = 0;

	_in = in;
	_interactive = false;

	Grammar g = new Grammar(this);
	g.parse();

	return 0;
    }

    private ContactPrx[] _foundContacts;
    private int _current;

    private Ice.Communicator _communicator;
    private PhoneBookPrx _phoneBook;

    private java.io.BufferedReader _in;
    private boolean _interactive;
}
