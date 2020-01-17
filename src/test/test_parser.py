import unittest

from parser import Parser


class MyTestCase(unittest.TestCase):

    def test_invalid_config(self):
        test_str = """
        external-hard-drive-dest: "/run/media/foo/bar"
        home-dir: "/home/foo"

        backup:
        - "Documents": "." # /home/foo/Documents -> /run/media/foo/bar/Documents
        - "/home/foo/Pictures": "/run/media/foo/bar" # /home/foo/Pictures -> /run/media/foo/bar/Pictures
        - "Desktop": "/run/media/foo/bar" # /home/foo/Desktop -> /run/media/foo/bar/Desktop
        - "/home/foo/Downloads": "Downloads" # /home/foo/Downloads -> /run/media/foo/bar/Downloads/Downloads
        """
        actual_backup_context = Parser.str_to_backup_context(test_str)
        expected_backup_context = {
            'external-hard-drive-dest': '/run/media/foo/bar',
            'home-dir': '/home/foo',
            'backup': [
                {
                    'Documents': '.',
                },
                {
                    '/home/foo/Pictures': '/run/media/foo/bar',
                },
                {
                    'Desktop': '/run/media/foo/bar',
                },
                {
                    '/home/foo/Downloads': 'Downloads',
                },
            ],
        }

        self.assertEqual(actual_backup_context, expected_backup_context)


if __name__ == '__main__':
    unittest.main()
