// **********************************************************************
//
// Copyright (c) 2001
// Mutable Realms, Inc.
// Huntsville, AL, USA
//
// All Rights Reserved
//
// **********************************************************************

#ifndef ICE_SSL_CONVERT_H
#define ICE_SSL_CONVERT_H

#include <Ice/BuiltinSequences.h>

namespace IceSSL
{

void ucharToByteSeq(unsigned char*, int, Ice::ByteSeq&);

unsigned char* byteSeqToUChar(const Ice::ByteSeq&);

}

#endif

