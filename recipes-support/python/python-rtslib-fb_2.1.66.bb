DESCRIPTION = "API for Linux kernel LIO SCSI target"
HOMEPAGE = "https://pypi.org"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://README.md;md5=dff2db8be7507553a3a7b0dd6423653d"
DEPENDS = "python"

SRC_URI = "https://files.pythonhosted.org/packages/15/33/879f9e2e97c1cfca0f0d67985769b6ee566ca37abf7c4333d99e9794a1cb/rtslib-fb-${PV}.tar.gz"
SRC_URI[md5sum] = "52780bd422740431d75d6c5e34ff2d54"
SRC_URI[sha256sum] = "9335152ff72afd2b384ba3d577beb939bcf43c2f04dfdcd0e31b2a204f91f674"

S = "${WORKDIR}/rtslib-fb-${PV}"

inherit setuptools

RDEPENDS_${PN} += "python-lang python-shell python-io"
