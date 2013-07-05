CS352 - Summer 2013

Project: Bittorrent - README
----------------------------

INTRODUCTION:
============

The purpose of this document is to specify and define the Project's following aspects:

* Structure
* Usage
* Relationships
* Ownership
* Implementation Details

It is very important we maintain this document organized and as completed as possible
since it would be a reference of your implemetation to the other team members working
on the same or different files.

REFERENCES:
===========

Interesting introduction: http://www.howtogeek.com/141257/htg-explains-how-does-bittorrent-work/

STRUCTURE:
==========

Roles:
	Server Side
		* Tracker: 	
				Participates in the torrent only by keeping track of the BitTorrent 
				clients connected to the swarm, not actually by downloading or uploading data.
		* Seed
	Client Side
		* Peer
		
IMPLEMENTATION
==============

The path for the implementation at initialization time is the following:
	
1.	RUBTClient holds the main method.
1.a		> Create a new Bittorrent instance
2.			> Bittorrent class
				In its constructor, Bittorrent will initialize its fields by reading a give 
				.torrent file in order to communicate with the tracker.

