DESCRIPTION = "A Python library for building configuration shells."
HOMEPAGE = "https://pypi.org"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://README.md;md5=327c1948e7711ef2d076b2afe8a34311"
DEPENDS = "python"

SRC_URI = "https://files.pythonhosted.org/packages/94/cc/27eb9499bd3500944308675956ea1e0efaa7d34d09d50c8fd271940101ef/configshell-fb-${PV}.tar.gz"
SRC_URI[md5sum] = "48cc6b3f1803d8fe68495e865a916203"
SRC_URI[sha256sum] = "40580b2e319d9aa0f83eb9e717ccd03601b74a358dd153d9e784d99014f6044c"

S = "${WORKDIR}/configshell-fb-${PV}"

inherit setuptools

RDEPENDS_${PN} += "python-lang python-shell python-io python-six"
