This layer supports the Mellanox BlueField SoC.

To use it, edit your conf/bblayers.conf file to include this directory
on the list of directories in BBLAYERS.  You should also similarly add
the meta-oe, meta-python, and meta-networking layers from
meta-openembedded, since packages in those layers are used in some of
the images included in meta-bluefield/recipes-bsp/images.

You should edit your conf/local.conf file to set MACHINE to
"bluefield".  To be able to build the same distro configurations that
is used in the Mellanox images (including using the same kernel
version shipped by Mellanox), you should also add:

include conf/bluefield.conf

This layer currently supports yocto Warrior (2.7.1).

Use the yocto-dependencies script to create all other required
meta layers to build yocto for BlueField.

