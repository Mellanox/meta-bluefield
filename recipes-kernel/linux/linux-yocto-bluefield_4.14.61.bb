inherit kernel
require recipes-kernel/linux/linux-yocto.inc

# Override SRC_URI in a copy of this recipe to point at a different source
# tree if you do not want to build from Linus' tree.
SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git;branch=linux-4.14.y;protocol=git;nocheckout=1;name=machine"

# Specify the "make savedefconfig" output to use for this kernel.
SRC_URI += "file://defconfig"
KCONFIG_MODE = "--alldefconfig"

LINUX_VERSION ?= "4.14.61"

# tag: v4.14.61 2ae6c0413b4768f9d8fc6f718a732f9dae014b67
SRCREV_machine = "2ae6c0413b4768f9d8fc6f718a732f9dae014b67"

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

# There appears to be a dependency bug regarding the shared artifacts
# directory and building out of tree kernel modules.  For example,
# if you clean the kernel and rebuild it, subsequent builds of out of
# tree modules will fail.  This appears to be because make-mod-scripts
# does not think anything changed (which is true but artifacts were
# deleted that need to be rebuilt).  Added this workaround for now.
# It unconditionally does what make-mod-scripts does.

DEPENDS += "bc-native"

do_shared_workdir_append() {
	unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
	oe_runmake CC="${KERNEL_CC}" LD="${KERNEL_LD}" AR="${KERNEL_AR}" \
	           -C ${STAGING_KERNEL_DIR} O=${STAGING_KERNEL_BUILDDIR} scripts prepare
}
