// **********************************************************************
//
// Copyright (c) 2001
// Mutable Realms, Inc.
// Huntsville, AL, USA
//
// All Rights Reserved
//
// **********************************************************************

#include <Ice/Application.h>
#include <Parser.h>

using namespace std;
using namespace Ice;

class PhoneBookClient : public Application
{
    virtual int run(int argc, char* argv[]);
};

int
main(int argc, char* argv[])
{
    PhoneBookClient app;
    return app.main(argc, argv, "config");
}

int
PhoneBookClient::run(int argc, char* argv[])
{
    int runParser(int, char*[], const CommunicatorPtr&);
    return runParser(argc, argv, communicator());
}
