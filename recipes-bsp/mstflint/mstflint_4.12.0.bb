#
# ex:ts=4:sw=4:sts=4:et
# -*- tab-width: 4; c-basic-offset: 4; indent-tabs-mode: nil -*-
#

SUMMARY = "mstflint"
SECTION = "mstflint"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://${WORKDIR}/mstflint-${PV}/COPYING;md5=44394078ccc59c749fdb44eb947cd797"

# The RPM used for this recipe requires xz tools to unpack and
# the poky classes don't detect that dependency automatically.
do_unpack[depends] += "xz-native:do_populate_sysroot"

COMPATIBLE_HOST = "aarch64.*-linux"

inherit autotools-brokensep pkgconfig systemd

SRC_URI  = "https://github.com/Mellanox/mstflint/releases/download/v4.12.0-1/mstflint-4.12.0-1.tar.gz"
SRC_URI[md5sum] = "7d3955ad9b09702d9e256be100107b49"
SRC_URI[sha256sum] = "6053989b9769720d822a4b848ed8977a967df493cf4f6063a89e662c99341f49"

S = "${WORKDIR}/mstflint-${PV}"

DEPENDS += "openssl zlib"
RDEPENDS_${PN} = "zlib"
RDEPENDS_${PN} += "openssl"
RDEPENDS_${PN} += "python python-stringold python-logging python-subprocess"

# We need a version of mstflint that support openssl 1.1
EXTRA_OECONF += "--disable-inband --disable-openssl --build=${BUILD_SYS} --host=${HOST_SYS}"
do_build[depends] += "glibc:do_populate_sysroot"
do_configure[postfuncs] = ""
do_package_qa[noexec] = "1"

do_unpack_extra() {
	cd ${S}
	sed -i -e "s@\*\.o@\$(shell /bin/ls *.o .libs/*.o)@" reg_access/Makefile* cmdif/Makefile*
	sed -i -e "s@tools_dev_types.o@.libs/tools_dev_types.o@" dev_mgt/Makefile*
	perl -ni -e "print unless /^LDFLAGS=.*usr.local.*/" configure*
	perl -ni -e "print unless /^CXXFLAGS=.*usr.local.*/" configure*
}

addtask unpack_extra after do_unpack before do_patch
