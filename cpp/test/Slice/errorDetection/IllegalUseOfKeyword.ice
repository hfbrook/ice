// **********************************************************************
//
// Copyright (c) 2001
// Mutable Realms, Inc.
// Huntsville, AL, USA
//
// All Rights Reserved
//
// **********************************************************************

exception module;
exception void
{
    int i;
};

struct exception;
struct class
{
    int i;
};

class interface;
class struct
{
    void f();
    int i;
};

interface local;
interface extends
{
    void f();
};

interface Foo
{
    void implements();
    
    int bar(string throws, long l, out bool void, out short s);

    int bar2(string s, long byte, out bool b, out short short);
};

class Bar
{
    string int;
    float long;
    double float;
    string double;
};

sequence<int> Object;

dictionary<int, int> LocalObject;

enum string
{
    a, b, c
};

enum E
{
    sequence,
    dictionary,
    enum
};
