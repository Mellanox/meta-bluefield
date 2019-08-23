inherit kernel
require recipes-kernel/linux/linux-yocto.inc

# Override SRC_URI in a copy of this recipe to point at a different source
# tree if you do not want to build from Linus' tree.
SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/torvalds/linux.git;protocol=git;nocheckout=1;name=machine"

# Specify the "make savedefconfig" output to use for this kernel.
SRC_URI += "file://defconfig"
KCONFIG_MODE = "--alldefconfig"

LINUX_VERSION ?= "4.14"

# tag: v4.14 bebc6082da0a9f5d47a1ea2edc099bf671058bd4
SRCREV_machine = "bebc6082da0a9f5d47a1ea2edc099bf671058bd4"

LINUX_VERSION_EXTENSION = ""

SRC_URI += "file://bluefield.patch"

PV = "${LINUX_VERSION}+git${SRCPV}"

COMPATIBLE_MACHINE = "bluefield"
