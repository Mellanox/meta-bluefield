inherit kernel
require recipes-kernel/linux/linux-yocto.inc

# Override SRC_URI in a copy of this recipe to point at a different source
# tree if you do not want to build from Linus' tree.
SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git;branch=linux-4.14.y;protocol=git;nocheckout=1;name=machine"

# Specify the "make savedefconfig" output to use for this kernel.
SRC_URI += "file://defconfig"
KCONFIG_MODE = "--alldefconfig"

LINUX_VERSION ?= "4.14.53"

# tag: v4.14.30 de8cdc5572311b0742eccf3c0cfd34af1e105904
SRCREV_machine = "fa745a1bd983b601c3f9c081ebf07dad9e0e3cb9"

LINUX_VERSION_EXTENSION = ""

SRC_URI += "file://bluefield.patch"

PV = "${LINUX_VERSION}+git${SRCPV}"

COMPATIBLE_MACHINE = "bluefield"

do_install_append() {
         if [ -d "${D}/${prefix}/${KERNEL_SRC_PATH}" ]; then
           ln -s ${D}/${prefix}/${KERNEL_SRC_PATH} \
             ${D}/${nonarch_base_libdir}/modules/${LINUX_VERSION}/build
         fi
}
