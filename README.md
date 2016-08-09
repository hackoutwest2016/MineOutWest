Mine out West
=============

A Minecraft mod that gives you a Way out West experience.

Team
====
* Mona Khoshoi
* Ludvig Strigeus
* Oscar Carlsson
* Simon Hofverberg
* Niklas Gustavsson
* Pär Bohrarper

Instructions
============
To download and convert the necessary music files, and to create the event json file:

    $ brew install ffmpeg --with-libvorbis
    $ cd scripts
    $ python lineup.py --lineup lineup.json --sounds sounds --download
    $ mv -f sounds.json sounds ../src/main/resources/assets/mineoutwest/
    $ mv -f events.json ../src/main/resources/
