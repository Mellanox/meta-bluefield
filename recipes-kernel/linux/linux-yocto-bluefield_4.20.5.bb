require recipes-kernel/linux/linux-yocto.inc
LIC_FILES_CHKSUM = "file://COPYING;md5=bbea815ee2795b2f4230826c0c6b8814"

KERNEL_CONFIG_COMMAND = "oe_runmake_call -C ${S} O=${B} olddefconfig"

DEPENDS += "openssl-native util-linux-native"

# Override SRC_URI in a copy of this recipe to point at a different source
# tree if you do not want to build from Linus' tree.
SRC_URI = "git://git.kernel.org/pub/scm/linux/kernel/git/stable/linux-stable.git;branch=linux-4.20.y;protocol=git;nocheckout=1;name=machine"

# Specify the "make savedefconfig" output to use for this kernel.
SRC_URI += "file://defconfig"
KCONFIG_MODE = "--alldefconfig"

LINUX_VERSION ?= "4.20.5"

# tag: v4.20.5 9f1a389a0b5b4004757759e26e2ff459016515ac
SRCREV_machine = "9f1a389a0b5b4004757759e26e2ff459016515ac"

LINUX_VERSION_EXTENSION = ""

SRC_URI += "file://bluefield.patch"

PV = "${LINUX_VERSION}+git${SRCPV}"

COMPATIBLE_MACHINE = "bluefield"

# These dependencies should be in newer versions of kernel-yocto.bbclass.  When we upgrade
# we can remove these.

do_kernel_configme[depends] += "virtual/${TARGET_PREFIX}binutils:do_populate_sysroot"
do_kernel_configme[depends] += "virtual/${TARGET_PREFIX}gcc:do_populate_sysroot"
do_kernel_configme[depends] += "bc-native:do_populate_sysroot bison-native:do_populate_sysroot flex-native:do_populate_sysroot"
do_kernel_configme[dirs] += "${S} ${B}"

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


do_shared_workdir_append() {
	unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
	oe_runmake CC="${KERNEL_CC}" LD="${KERNEL_LD}" AR="${KERNEL_AR}" \
	           -C ${STAGING_KERNEL_DIR} O=${STAGING_KERNEL_BUILDDIR} scripts prepare
}
