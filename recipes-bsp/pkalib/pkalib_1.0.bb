SUMMARY = "Mellanox PKA library"

DESCRIPTION = "User space library and openssl engine for Mellanox PKA acceleration."

# This recipe currently only supports openssl 1.0.
# While Yocto/Poky does support openssl 1.1, the openssh recipe
# still currently requires openssl 1.0 so if you wish to use
# openssh you need to use openssl 1.0 (or update openssh, which would
# be non-trivial).  Note that openssl 1.1 has not yet received FIPS
# certification which is a good reason to stick with openssl 1.0
# for now.

LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://COPYING;md5=3567e19bd6bec0ab30601f4b69a24e2d"

inherit lib_package pkgconfig autotools

SRC_URI = "file://pkalib-1.0.tar.xz"

SRC_URI[md5sum] = "3317d865bb305d652636865f86239cc1"
SRC_URI[sha256sum] = "c39d3143841b5d7ef69af708676f9466bf2cb363dbdbf71f3f474e1a34a625a2"

RPROVIDES_${PN} =+ " libpka1 libpka-dev libpka-dbg libpka-openssl-engine libpka-tests libpka-doc"

DEPENDS += "openssl"
RDEPENDS_${PN} += "openssl"

ASNEEDED = ""

do_compile_append() {
	oe_runmake -C engine
	oe_runmake -C tests
}

do_install () {
    [ -d "${B}" ] || exit 1
    [ -d "${S}" ] || exit 1
    cd ${B} || exit 1

    mkdir -p ${D}${libdir}/engines

    cp lib/.libs/libPKA.so ${D}${libdir}/libPKA.so.1
    ln -rsf ${D}${libdir}/libPKA.so.1  ${D}${libdir}/libPKA.so

    # Note that openssl 1.0 appends lib to the library so we do that.
    # If you want to use openssl 1.1 you will need to tweak this
    # part of the recipe.
    cp engine/.libs/libbfengine.so ${D}${libdir}/engines
    ln -rsf ${D}${libdir}/engines/libbfengine.so  ${D}${libdir}/engines/libpka.so

    mkdir -p ${D}${includedir}
    cp -r ${S}/include/*.h ${D}${includedir}
    cp ${S}/lib/*.h ${D}${includedir}
    cp ${S}/engine/helper/*.h ${D}${includedir}

    mkdir -p ${D}${docdir}/pka
    cp -r ${B}/doc ${D}${docdir}/pka
    cp ${S}/README ${D}${docdir}/pka
    cp ${S}/README.engine ${D}${docdir}/pka
    cp ${S}/README.tests ${D}${docdir}/pka

    mkdir -p ${D}${bindir}/pka/tests
    cp ${B}/tests/.libs/pka_test_performance ${D}${bindir}
    cp ${B}/tests/.libs/pka_test_power ${D}${bindir}
    cp ${B}/tests/.libs/pka_test_validation ${D}${bindir}
}

INSANE_SKIP_${PN}-openssl-engine += "dev-so"

PACKAGES_prepend = "${PN}-openssl-engine ${PN}-tests "
FILES_${PN}-openssl-engine = "${libdir}/engines/*.so"
FILES_${PN}-tests = "${bindir}/*"
