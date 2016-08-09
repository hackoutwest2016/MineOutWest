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
    $ python oggify.py sounds
    $ python lineup.py sounds lineup.csv
    $ mv -f sounds.json events.json sounds ../src/main/resources/assets/mineoutwest
